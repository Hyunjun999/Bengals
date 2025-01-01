from shapely.ops import unary_union
import pandas as pd
import sys
import geopandas as gpd
import numpy as np
import random
import multiprocessing as mp
from gerrychain.tree import bipartition_tree

from gerrychain import (
    Partition,
    Graph,
    MarkovChain,
    updaters,
    constraints,
    accept,
    tree,
)
from gerrychain.proposals import recom
from functools import partial
from tqdm import tqdm
import pdb


def gluing_trial(
    subgraph_sizes, flag, super_dist, smd_dict, subgraph, bucket, cand, available
):
    for district_size in subgraph_sizes:
        if flag != False:
            for iter in range(district_size):
                if len(available) > 0:
                    if iter == 0:
                        distId = random.choice(available)
                        available.remove(distId)
                        super_dist = smd_dict[distId]
                        bucket.append(distId)
                    else:
                        if len(cand) == 0:
                            flag = False
                            print("Could not make a subgraph...doing it again")
                            return flag, None
                        distId = random.choice(cand)
                        available.remove(distId)
                        super_dist = unary_union([super_dist, smd_dict[distId]])
                        bucket.append(distId)
                    cand = []
                    for id in available:
                        if smd_dict[id].intersects(super_dist):
                            cand.append(id)
                else:
                    flag = False
            if flag == False:
                print("Could not make a subgraph...doing it again")
                return flag, None
            subgraph.append(bucket)
            bucket = []
        else:
            print("Could not make a subgraph...doing it again")
            return flag, None
    print("Successfully created MMD")
    return flag, subgraph


def mmd_glue(ensemble, mmd_split):
    subgraph_sizes = mmd_split
    flag = False

    while flag == False:
        flag = True
        super_dist = None
        smd_dict = {}
        subgraph, bucket, cand, available = [], [], [], []
        for idx, row in ensemble.iterrows():
            available.append((row["districtId"]))
            smd_dict[row["districtId"]] = row["geometry"]

        flag, subgraph = gluing_trial(
            subgraph_sizes,
            flag,
            super_dist,
            smd_dict,
            subgraph,
            bucket,
            cand,
            available,
        )

    print("subgraph: {}".format(subgraph))
    return subgraph


def format_mmd_dataframe(mmd_dict, smd_ensem_cp, id, lst, col_list):
    for i in range(1, len(lst) + 1):
        locked = smd_ensem_cp.loc[smd_ensem_cp["mmd_dist"] == i]
        num_dist = 0
        mmd_dict["plan_no"].append(id + "_mmd")
        mmd_dict["mmd_dist"].append(i)
        for idx, row in locked.iterrows():
            num_dist += 1
            for col in col_list:
                if col not in mmd_dict.keys():
                    if col == "geometry":
                        mmd_dict[col] = []
                        mmd_dict[col].append(row[col])
                    else:
                        mmd_dict[col] = []
                        mmd_dict[col].append(int(row[col]))
                else:
                    if len(mmd_dict[col]) < i:
                        mmd_dict[col].append(row[col])
                    else:
                        if col == "geometry":
                            mmd_dict[col][i - 1] = unary_union(
                                [row[col], mmd_dict[col][i - 1]]
                            )
                        else:
                            mmd_dict[col][i - 1] += int(row[col])

        mmd_dict["num_dist"].append(num_dist)

    return mmd_dict


class Ballot:
    def __init__(self, candidates, num_cand, party):

        self.num_cand = num_cand
        self.candidates = candidates
        self.paper_dict = self.generate_random_rankings()
        self.party = party

    def generate_random_rankings(self):
        # Randomly shuffle the list of candidates to create a ranking

        rep_ranked_list = self.candidates[: self.num_cand // 2]
        dem_ranked_list = self.candidates[self.num_cand // 2 :]

        random.shuffle(rep_ranked_list)
        random.shuffle(dem_ranked_list)
        total_rank = rep_ranked_list + dem_ranked_list
        return total_rank


def winner_process(elected, elec_res, cur_thres, champ_name, champ_vote):
    # Check how much elected_candidate exceeds thershold
    if elec_res[champ_name][0] >= cur_thres:
        random.shuffle(elec_res[champ_name][1])
        init_champ_vote = champ_vote

        # Distributing the votes (over threshold) to the next ranked candidate
        for idx in range(elec_res[champ_name][0] - int(cur_thres)):
            over_ballot = elec_res[champ_name][1].pop(0)
            # pdb.set_trace()
            while True:
                if len(over_ballot.paper_dict) > 0:
                    next_cand = over_ballot.paper_dict.pop(0)
                    if next_cand in elec_res.keys():
                        elec_res[next_cand][0] += 1
                        elec_res[next_cand][1].append(over_ballot)
                        champ_vote -= 1
                        break
                    else:
                        None
                else:
                    raise ValueError("The winner algorithm seems to be wrong...")
        print(champ_name + " Elected!")
        elected[champ_name] = [init_champ_vote, champ_vote]  # [over_thres, thres]
        del elec_res[champ_name]

        return elected, elec_res


def loser_process(elec_res):
    print("No winner... choosing loser!")
    loser_name = None
    loser_vote = 1e50
    for key in elec_res.keys():  # getting the loser
        if loser_vote > elec_res[key][0]:
            loser_name = key
            loser_vote = elec_res[key][0]

    # Distributing the loser's votes to the next ranked candidate
    for idx in range(loser_vote):
        loser_ballot = elec_res[loser_name][1].pop(0)
        # pdb.set_trace()
        while True:
            if len(loser_ballot.paper_dict) > 0:
                next_cand = loser_ballot.paper_dict.pop(0)
                if next_cand in elec_res.keys():
                    elec_res[next_cand][1].append(loser_ballot)
                    break
            else:
                raise ValueError("The loser algorithm seems to be wrong...")

    if len(elec_res[loser_name][1]) == 0:
        print(loser_name + " is eliminated...")
        del elec_res[loser_name]
        return elec_res
    else:
        raise ValueError("The popping algorithm seems to be wrong...")


def create_smd_ensem(id, partition, df_template, smd_ensemble_template):
    # Making the Dataframe for the result
    precincts = []

    for node in partition.graph.nodes:
        single_precinct = []
        districtId = "{}_{}".format(id, str(partition.assignment[node]))
        single_precinct.append(districtId)

        for column in df_template:
            if column != "districtId":
                single_precinct.append(partition.graph.nodes[node][column])
        single_precinct.append(partition.graph.nodes[node]["geometry"].buffer(0))

        precincts.append(single_precinct)

    df_precincts = pd.DataFrame(precincts, columns=smd_ensemble_template)

    # sum up numeric/geometry data of precincts by districtId
    districts_info = df_precincts[df_template].groupby("districtId").sum().reset_index()
    districts_geo = (
        df_precincts[["districtId", "geometry"]]
        .groupby("districtId")["geometry"]
        .apply(unary_union)
        .reset_index()
    )

    districts = districts_info.merge(districts_geo, on="districtId")

    return districts


def create_mmd_plan(smd_districts, id, lst):

    subgraph = mmd_glue(smd_districts, lst)
    smd_districts_cp = smd_districts.copy()
    smd_districts_cp["mmd_dist"] = 0
    for idx in range(len(subgraph)):
        for elem in subgraph[idx]:
            smd_districts_cp.loc[smd_districts_cp["districtId"] == elem, "mmd_dist"] = (
                idx + 1
            )

    col_list = smd_districts_cp.columns.tolist()
    col_list.remove("districtId")
    col_list.remove("mmd_dist")
    print(col_list)

    mmd_dict = {}
    mmd_dict["plan_no"], mmd_dict["mmd_dist"], mmd_dict["num_dist"] = [], [], []

    mmd_dict = format_mmd_dataframe(mmd_dict, smd_districts_cp, id, lst, col_list)

    mmd_plan = pd.DataFrame(mmd_dict)
    mmd_plan = gpd.GeoDataFrame(mmd_plan, geometry="geometry", crs="EPSG:4269")

    return mmd_plan


def create_election_setting(mmd_plan, elec_mmd, process_params, cand_format, threshold):
    for idx, row in mmd_plan.iterrows():  # for each super-district
        district_ballot, candidate_r, candidate_d = [], [], []
        tot_voter = 0

        elec_mmd["plan_no"].append(row["plan_no"])
        elec_mmd["mmd_dist"].append(row["mmd_dist"])
        elec_mmd["num_dist"].append(row["num_dist"])

        for i in range(row["num_dist"]):
            candidate_r.append("R_{}".format(i))
            candidate_d.append("D_{}".format(i))
        for i in range(row["num_dist"]):
            candidate_d.append("R_{}".format(i))
            candidate_r.append("D_{}".format(i))

        if len(cand_format) < len(candidate_r):
            cand_format = candidate_r

        for i in range(int(row["PRE20_R"])):
            district_ballot.append(Ballot(candidate_r, row["num_dist"] * 2, "REP"))
            tot_voter += 1
        for i in range(int(row["PRE20_D"])):
            district_ballot.append(Ballot(candidate_d, row["num_dist"] * 2, "DEM"))
            tot_voter += 1

        process_params.append(
            (
                district_ballot,
                row["num_dist"],
                1 / row["num_dist"] * threshold,
                cand_format,
                tot_voter,
            )
        )

    return elec_mmd, cand_format, process_params


def run_mmd_election(param):
    ballot_list, win_num, threshold, template, tot_voter = param
    print("Required {} election".format(win_num))

    elected, elec_res = {}, {}
    # elec_res = {"R_1" : [0,[]], "R_2" : [0,[]], "R_3" : [0,[]], "R_4" : [0,[]],
    #             "D_1" : [0,[]], "D_2" : [0,[]], "D_3" : [0,[]], "D_4" : [0,[]]}
    for item in template:
        elec_res[item] = [0, []]
    for item in ballot_list:
        cand = item.paper_dict.pop(0)
        elec_res[cand][1].append(item)

    while len(elected) < win_num:

        cur_thres, champ_vote = 0, 0
        champ_name = None

        for key in elec_res.keys():
            elec_res[key][0] = len(elec_res[key][1])

        cur_thres = tot_voter * threshold

        for key in elec_res.keys():  # getting the top 1
            if champ_vote < elec_res[key][0]:
                champ_name = key
                champ_vote = elec_res[key][0]

        if elec_res[champ_name][0] >= cur_thres:
            elected, elec_res = winner_process(
                elected, elec_res, cur_thres, champ_name, champ_vote
            )
        else:  # No candidate has votes above threshold : picking loser & eliminate
            elec_res = loser_process(elec_res)
    return elected, elec_res


def create_election_data(result, elec_mmd, cand_init_flag, cand_format, item):
    for item_res in result:
        # (DEBUG PURPOSE) elec_res : The total container of the election (ballots)
        elec, elec_res = item_res

        if cand_init_flag:  # Initial Setting
            for cand in cand_format:
                elec_mmd[cand] = []
            cand_init_flag = False

        for cand in cand_format:
            if cand in elec.keys():
                elec_mmd[cand].append(elec[cand][0])
            else:
                elec_mmd[cand].append(0)

    # IF the candidiate is eliminated from the election, have the
    # vote number be NA in the dataframe
    frame_len = len(item[3])
    for cand in elec_mmd.keys():
        if len(elec_mmd[cand]) < frame_len:
            for idx in range(frame_len - len(elec_mmd[cand])):
                elec_mmd[cand].append(pd.NA)

    print(elec_mmd)
    elec_mmd_data = pd.DataFrame(elec_mmd)

    return elec_mmd_data


def mmd_election_simulation(mmd_plan, item):
    elec_mmd = {}
    elec_mmd["plan_no"], elec_mmd["mmd_dist"], elec_mmd["num_dist"] = [], [], []
    process_params, cand_format = [], []

    cand_init_flag = True
    ELECTION_THRES_SCALE = 0.7

    elec_mmd, cand_format, process_params = create_election_setting(
        mmd_plan, elec_mmd, process_params, cand_format, ELECTION_THRES_SCALE
    )

    result = []
    for elec_params in process_params:
        result.append(run_mmd_election(elec_params))  # Real Election process

    elec_mmd_data = create_election_data(
        result, elec_mmd, cand_init_flag, cand_format, item
    )

    return elec_mmd_data


def run_chain(params):

    (
        id_num,
        item,
        proposal,
        pop_constraint,
        initial_partition,
        smd_df_template,
        smd_ensemble_template,
    ) = params

    TOTAL_STEPS = 5000

    chain = MarkovChain(
        proposal=proposal,
        constraints=pop_constraint,
        accept=accept.always_accept,
        initial_state=initial_partition,
        total_steps=TOTAL_STEPS,
    )

    for partition in chain.with_progress_bar():
        pass  # runs iterations

    print("Creating smd...")
    id = "{}_{}".format(item[-1], str(id_num))  # AL_[n-th district plan]
    smd_plan = create_smd_ensem(id, partition, smd_df_template, smd_ensemble_template)

    # Building MMD - Minority & overall election
    print("Creating mmd...")
    mmd_plan = create_mmd_plan(smd_plan, id, item[3])
    print("Done mmd...")

    # Simulating mmd election
    print("Running election simulation...")
    elec_mmd_data = mmd_election_simulation(mmd_plan, item)
    print("Done election simulation...!")

    return smd_plan, mmd_plan, elec_mmd_data


def chain_setting(gdf, graph, item):
    seawulf_updaters = {}
    CONSTRAINT_PERCENT = 0.2
    EPSILON = 0.05
    NODE_REPEAT = 10
    MAX_ATTEMPT = 5000
    for column in gdf.columns:
        if column != "geometry":
            seawulf_updaters[column] = updaters.Tally(column)

    ideal_population = gdf["POP"].sum() / item[2]
    dist_list = list(gdf["DISTRICT"].unique())

    random_assignment = tree.recursive_tree_part(
        graph, dist_list, ideal_population, "POP", EPSILON
    )

    initial_partition = Partition(
        graph=graph, assignment=random_assignment, updaters=seawulf_updaters
    )

    proposal = partial(
        recom,
        pop_col="POP",
        pop_target=ideal_population,
        epsilon=EPSILON,
        node_repeats=NODE_REPEAT,
        method=partial(
            bipartition_tree,
            max_attempts=MAX_ATTEMPT,
        ),
    )

    pop_constraint = [
        constraints.within_percent_of_ideal_population(
            initial_partition, CONSTRAINT_PERCENT, pop_key="POP"
        )
    ]

    return initial_partition, proposal, pop_constraint


def seawulf_parallel(param):
    gdf, graph, item, idx = param

    try:
        initial_partition, proposal, pop_constraint = chain_setting(gdf, graph, item)

        smd_df_template = []
        smd_df_template.append("districtId")
        for column in gdf.columns:
            if column not in ["index", "REL_ID", "NEIGH_LIST", "DISTRICT", "geometry"]:
                smd_df_template.append(column)

        smd_ensemble_template = []
        smd_ensemble = create_smd_dataframe(smd_ensemble_template, gdf)

        #  Running the Markov Chain
        param_list = (
            idx,
            item,
            proposal,
            pop_constraint,
            initial_partition,
            smd_df_template,
            smd_ensemble_template,
        )

        results = run_chain(param_list)
        smd_districts, mmd_ensemble, elec_mmd_data = results

        smd_ensemble = pd.concat([smd_ensemble, smd_districts], ignore_index=True)

        return smd_ensemble, mmd_ensemble, elec_mmd_data

    except Exception as e:
        print(e)
        return None


def format_multiprocess_data(res, smd_ensemble, mmd_data_flag, mmd_ensem_flag, item):
    for result in res:
        if result != None:
            smd_ensem, mmd_ensem, elec_mmd_data = result
            # print(district.head())
            temp_smd_ensemble = gpd.read_file(item[1])
            smd_ensemble = pd.concat(
                [temp_smd_ensemble, smd_ensem], ignore_index=True
            )  # SMD
            smd_ensemble_gdf = gpd.GeoDataFrame(
                smd_ensemble, geometry="geometry", crs="EPSG:4269"
            )
            smd_ensemble_gdf.to_file(item[1], driver="ESRI Shapefile")

            if mmd_data_flag == False:
                mmd_data_flag = True
                orig_mmd_data = elec_mmd_data
                orig_mmd_data.to_csv(item[4])
            else:
                temp_mmd_data = pd.read_csv(item[4], index_col=0)
                orig_mmd_data = pd.concat(
                    [temp_mmd_data, elec_mmd_data], ignore_index=True
                )
                orig_mmd_data.to_csv(
                    item[4],
                )

            if mmd_ensem_flag == False:
                mmd_ensem_flag = True
                orig_mmd_ensem = mmd_ensem
                orig_mmd_ensem.to_file(item[5])
            else:
                temp_mmd_ensem = gpd.read_file(item[5])
                orig_mmd_ensem = pd.concat(
                    [temp_mmd_ensem, mmd_ensem], ignore_index=True
                )
                orig_mmd_ensem.to_file(item[5], driver="ESRI Shapefile")

        else:
            print("Error Occured: None Result")

    return mmd_data_flag, mmd_ensem_flag


def create_smd_dataframe(smd_ensemble_template, gdf):
    smd_ensemble_template.append("districtId")
    for column in gdf.columns:
        if column not in ["index", "REL_ID", "NEIGH_LIST", "DISTRICT"]:
            smd_ensemble_template.append(column)

    smd_ensemble = pd.DataFrame(columns=smd_ensemble_template)
    return smd_ensemble


def main():

    pa_file = [
        "./dataset/New_Precincts/PA_neigh_dist_15.shp",
        "./dataset/Gerrychain/PA_ensemble.shp",
        18,
        [5, 5, 5, 3],
        "./dataset/Gerrychain/PA_mmd_elec.csv",
        "./dataset/Gerrychain/PA_mmd_ensemble.shp",
        "PA",
    ]
    ms_file = [
        "./dataset/New_Precincts/MS_neigh_dist_15.shp",
        "./dataset/Gerrychain/MS_ensemble.shp",
        4,
        [4],
        "./dataset/Gerrychain/MS_mmd_elec.csv",
        "./dataset/Gerrychain/MS_mmd_ensemble.shp",
        "MS",
    ]
    al_file = [
        "./dataset/New_Precincts/AL_neigh_dist_15/AL_neigh_dist_15.shp",
        "./dataset/Gerrychain/AL_ensemble.shp",
        7,
        [4, 3],
        "./dataset/Gerrychain/AL_mmd_elec.csv",
        "./dataset/Gerrychain/AL_mmd_ensemble.shp",
        "AL",
    ]

    # pa_file = ['./New_precincts/PA_neigh_dist_15/PA_neigh_dist.shp',
    #            './Gerrychain/PA_smd_ensemble.shp', 18, [5,5,5,3],
    #            "./Gerrychain/PA_mmd_elec.csv",
    #            './Gerrychain/PA_mmd_ensemble.shp', "PA"]
    # ms_file = ['./New_precincts/MS_neigh_dist_15/MS_neigh_dist_15.shp',
    #            './Gerrychain/MS_smd_ensemble.shp', 4, [4],
    #            "./Gerrychain/MS_mmd_elec.csv",
    #            './Gerrychain/MS_mmd_ensemble.shp', "MS"]
    # al_file = ['./New_precincts/AL_neigh_dist_15/AL_neigh_dist_15.shp',
    #            './Gerrychain/AL_smd_ensemble.shp', 7, [4,3],
    #            "./Gerrychain/AL_mmd_elec.csv",
    #            './Gerrychain/AL_mmd_ensemble.shp', "AL"]

    # precinct_file = [pa_file, ms_file, al_file]
    precinct_file = [al_file]  # for test

    cpus = mp.cpu_count()
    print("Number of cpu's to process: %d" % cpus)

    for item in precinct_file:
        gdf = gpd.read_file(item[0])
        graph = Graph.from_geodataframe(gdf, ignore_errors=True)
        CORE_UTILIZE = 80
        ITERATION = 63

        # Multiprocessing Setting
        # seawulf_param_list = [[(gdf, graph, item, 80*j+i) for i in range(0,63)]
        #                     for j in range(0,1)] # ==> 5040 plans
        multiprocess_param_list = [
            [(gdf, graph, item, CORE_UTILIZE * j + i) for i in range(0, CORE_UTILIZE)]
            for j in range(0, ITERATION)
        ]

        # Exporting Dataframe Format setting
        smd_ensemble_template = []
        smd_ensemble = create_smd_dataframe(smd_ensemble_template, gdf)
        smd_ensemble_gdf = gpd.GeoDataFrame(
            smd_ensemble, geometry="geometry", crs="EPSG:4269"
        )
        smd_ensemble_gdf.to_file(item[1], driver="ESRI Shapefile")

        mmd_data_flag, mmd_ensem_flag = False, False

        # Multiprocessing Exection
        for sliced_idx in range(len(multiprocess_param_list)):

            with mp.Pool(len(multiprocess_param_list[sliced_idx])) as pool:
                res = pool.map(seawulf_parallel, multiprocess_param_list[sliced_idx])

            mmd_data_flag, mmd_ensem_flag = format_multiprocess_data(
                res, smd_ensemble, mmd_data_flag, mmd_ensem_flag, item
            )
        print("{} SMD & MMD Ensemble Done...".format(item[-1]))

    print("All processes Complete...!")


if __name__ == "__main__":
    main()
