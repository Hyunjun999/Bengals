import json, geopandas as gpd, numpy as np, pandas as pd, random
import matplotlib.pyplot as plt
from collections import defaultdict

data_format = {
    "state": "",
    "smd": {
        "summary": {
            "num_dist_plan": 0,
            "avg_min_max_diff": 0.0,  # percentage
            "avg_num_minor_representatives": {"non_wht": 0.0},  # float
            "avg_party_split": {
                "republican": 0.0,  # float
                "democratic": 0.0,
            },  # Total # of rep + dem repre / 5000
            "seats_votes": [
                {"republican": 0.0, "democratic": 0.0},
                {"republican": 0.0, "democratic": 0.0},
            ],
            "bias": 0.0,  # percentage
            "symmetry": 0.0,  # No unit
            "responsiveness": {"republican": 0.0, "democratic": 0.0},  # No unit
        },
        "racial": {
            "racial_box_whisker": {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            },
            "op_districts": {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            },
            "op_representatives": {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            },
        },
        "party": {
            "party_box_whisker": {
                "republican": [],
                "democratic": [],
            },
            "party_splits": [],
        },
    },
    "mmd": {
        "summary": {
            "num_dist_plan": 0,
            "avg_min_max_diff": 0.0,  # percentage
            "avg_num_minor_representatives": {"non_wht": 0.0},  # float
            "avg_party_split": {
                "rep": 0.0,  # float
                "dem": 0.0,
            },  # Total # of rep + dem repre / 5000
            "seats_votes": [
                {"republican": 0.0, "democratic": 0.0},
                {"republican": 0.0, "democratic": 0.0},
            ],
            "bias": 0.0,  # percentage
            "symmetry": 0.0,  # No unit
            "responsiveness": {"republican": 0.0, "democratic": 0.0},  # No unit
            "layout": [],
        },
        "racial": {
            "racial_box_whisker": {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            },
            "op_districts": {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            },
            "op_representatives": {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            },
        },
        "party": {
            "party_box_whisker": {
                "republican": [],
                "democratic": [],
            },
            "party_splits": [],
        },
        "enacted_vs_avg": {
            "enacted": {
                "republican": 0,
                "democratic": 0,
                "num_op_representatives": 0,
                "seats_votes": [],
            },
            "avg_mmd": {
                "republican": 0.0,
                "democratic": 0.0,
                "num_op_representatives": 0.0,
                "seats_votes": [],  # AVG => div by 5
            },
        },
    },
}


def op_bar(ensemble_path, plan_type):
    op_bar = {
        "op_district_bar": {
            plan_type: {
                "blk": [],
                # "asn": [],
                "hsp": [],
                "non_wht": [],
            }
        },
        "op_representatives_bar": {
            plan_type: {
                "blk": [],
                # "asn": [],
                "hsp": [],
                "non_wht": [],
            }
        },
    }
    gdf = gpd.read_file(ensemble_path)
    if plan_type == "smd":
        if gdf["districtId"][0].split("_")[0] == "MS":
            num_district_by_graph = 4
        elif gdf["districtId"][0].split("_")[0] == "AL":
            num_district_by_graph = 7
        elif gdf["districtId"][0].split("_")[0] == "PA":
            num_district_by_graph = 18
        gdf = gdf.groupby(gdf["districtId"].str.split("_").str[1])
    elif plan_type == "mmd":
        if gdf["plan_no"][0].split("_")[0] == "MS":
            num_district_by_graph = 1
        elif gdf["plan_no"][0].split("_")[0] == "AL":
            num_district_by_graph = 2
        elif gdf["plan_no"][0].split("_")[0] == "PA":
            num_district_by_graph = 4
        gdf = gdf.groupby(gdf["plan_no"].str.split("_").str[1])
    total_non_wht_op_repre = 0
    for graph_id, group in gdf:
        for i in range(0, len(group), num_district_by_graph):
            single_plan = group.iloc[i : i + num_district_by_graph]
            print(f"Graph {graph_id} - Rows {i} to {i + num_district_by_graph}")
            blk_op_district, asn_op_district, hsp_op_district, non_wht_op_district = (
                0,
                0,
                0,
                0,
            )
            blk_op_repre, asn_op_repre, hsp_op_repre, non_wht_op_repre = 0, 0, 0, 0
            if plan_type == "smd":
                for _, r in single_plan.iterrows():
                    if float(r["POP_BLACK"]) / float(r["POP"]) > 0.5:
                        blk_op_district += 1
                        blk_op_repre += 1

                    if float(r["POP_HISP"]) / float(r["POP"]) > 0.5:
                        hsp_op_district += 1
                        hsp_op_repre += 1

                    if (
                        float(r["POP_ASIAN"])
                        + float(r["POP_BLACK"])
                        + float(r["POP_HISP"])
                    ) / float(r["POP"]) > 0.5:
                        non_wht_op_district += 1
                        non_wht_op_repre += 1
            elif plan_type == "mmd":
                for _, r in single_plan.iterrows():
                    num_dist = r["num_dist"]

                    if num_dist == 3:
                        op_threshold = 0.33
                    elif num_dist == 4:
                        op_threshold = 0.25
                    elif num_dist == 5:
                        op_threshold = 0.20

                    blk_possible_op_repre = int(
                        (int(r["POP_BLACK"]) / int(r["POP"]))
                        // op_threshold  # r["op_threshold"]
                    )
                    hsp_possible_op_repre = int(
                        (int(r["POP_HISP"]) / int(r["POP"]))
                        // op_threshold  # r["op_threshold"]
                    )
                    non_wht_possible_op_repre = int(
                        (
                            (
                                int(r["POP_BLACK"])
                                + int(r["POP_HISP"])
                                + int(r["POP_ASIAN"])
                            )
                            / int(r["POP"])
                        )
                        // op_threshold
                    )
                    if (
                        int(r["POP_BLACK"]) / int(r["POP"]) > op_threshold
                    ):  # r["op_threshold"]
                        blk_op_district += 1
                        blk_op_repre += (
                            blk_possible_op_repre
                            if blk_possible_op_repre <= num_dist
                            else num_dist
                        )

                    if (
                        int(r["POP_HISP"]) / int(r["POP"]) > op_threshold
                    ):  # r["op_threshold"]
                        hsp_op_district += 1
                        hsp_op_repre += (
                            hsp_possible_op_repre
                            if hsp_possible_op_repre <= num_dist
                            else num_dist
                        )

                    if (
                        int(r["POP_BLACK"]) + int(r["POP_HISP"]) + int(r["POP_ASIAN"])
                    ) / int(
                        r["POP"]
                    ) > op_threshold:  # r["op_threshold"]
                        non_wht_op_district += 1
                        non_wht_op_repre += (
                            non_wht_possible_op_repre
                            if non_wht_possible_op_repre <= num_dist
                            else num_dist
                        )
            op_bar["op_district_bar"][plan_type]["blk"].append(
                {"num_op_districts": blk_op_district}
            )
            # op_bar["op_district_bar"][plan_type]["asn"].append(
            #     {"num_op_districts": hsp_op_district}
            # )
            op_bar["op_district_bar"][plan_type]["hsp"].append(
                {"num_op_districts": asn_op_district}
            )
            op_bar["op_district_bar"][plan_type]["non_wht"].append(
                {"num_op_districts": non_wht_op_district}
            )
            op_bar["op_representatives_bar"][plan_type]["blk"].append(
                {"num_op_representatives": blk_op_repre}
            )
            # op_bar["op_representatives_bar"][plan_type]["asn"].append(
            #     {"num_op_representatives": hsp_op_repre}
            # )
            op_bar["op_representatives_bar"][plan_type]["hsp"].append(
                {"num_op_representatives": asn_op_repre}
            )
            op_bar["op_representatives_bar"][plan_type]["non_wht"].append(
                {"num_op_representatives": non_wht_op_repre}
            )
            total_non_wht_op_repre += non_wht_op_repre
    op_bar["avg_op_repre"] = int(total_non_wht_op_repre / len(gdf) * 10**3) / 10**3
    return op_bar


def party_split(ensemble_path, plan_type, state):
    bar = {
        "party_splits_bar": {plan_type: []},
    }
    gdf = gpd.read_file(ensemble_path)
    graph_dominance_counts = defaultdict(lambda: {"republican": 0, "democratic": 0})

    if plan_type == "smd" and state == "MS":
        district_num_by_state = 4
    elif plan_type == "smd" and state == "AL":
        district_num_by_state = 7
    elif plan_type == "smd" and state == "PA":
        district_num_by_state = 18
    elif plan_type == "mmd" and state == "MS":
        district_num_by_state = 1
    elif plan_type == "mmd" and state == "AL":
        district_num_by_state = 2
    elif plan_type == "mmd" and state == "PA":
        district_num_by_state = 4
    for _, row in gdf.iterrows():
        if plan_type == "smd":
            graph_id = row["districtId"].split("_")[1]
            if row["PRE20_R"] > row["PRE20_D"]:
                graph_dominance_counts[graph_id]["republican"] += 1
            elif row["PRE20_R"] < row["PRE20_D"]:
                graph_dominance_counts[graph_id]["democratic"] += 1

        elif plan_type == "mmd":
            graph_id = row["plan_no"].split("_")[1]
            if row["PRE20_R"] > row["PRE20_D"]:
                graph_dominance_counts[graph_id]["republican"] += 1
            elif row["PRE20_R"] < row["PRE20_D"]:
                graph_dominance_counts[graph_id]["democratic"] += 1

            # Opportunity

    # Calculate the distribution of dominant districts in each graph
    for graph_id, dominance in graph_dominance_counts.items():
        rep_dominant = dominance["republican"]
        dem_dominant = dominance["democratic"]

        # Ensure `party_splits_bar` has enough entries for the current counts
        max_count = max(rep_dominant, dem_dominant)
        while len(bar["party_splits_bar"][plan_type]) <= max_count:
            bar["party_splits_bar"][plan_type].append(
                {
                    "name": len(bar["party_splits_bar"][plan_type]),
                    "republican": 0,
                    "democratic": 0,
                }
            )

        bar["party_splits_bar"][plan_type][rep_dominant]["republican"] += 1
        bar["party_splits_bar"][plan_type][dem_dominant]["democratic"] += 1

    total_republican = sum(
        dominance["republican"] for dominance in graph_dominance_counts.values()
    )
    total_democratic = sum(
        dominance["democratic"] for dominance in graph_dominance_counts.values()
    )
    bar["avg_rep"] = total_republican / (len(gdf) / district_num_by_state)
    bar["avg_dem"] = total_democratic / (len(gdf) / district_num_by_state)
    return bar


def box_whisker(
    ensemble_path, enacted_path, enacted_vote_path, plan_type, plan_number, box_type
):
    gdf = gpd.read_file(ensemble_path)
    plan_type_key = box_type
    if box_type == "racial":
        population_percentages = {
            plan_type_key: {
                "blk": [[] for _ in range(plan_number)],
                "hsp": [[] for _ in range(plan_number)],
                "non_wht": [[] for _ in range(plan_number)],
            }
        }
    elif box_type == "party":
        population_percentages = {
            plan_type_key: {
                "republican": [[] for _ in range(plan_number)],
                "democratic": [[] for _ in range(plan_number)],
            }
        }
    for _, row in gdf.iterrows():
        if plan_type == "smd":
            total_pop = int(row["POP"])
            graph_id = int(row["districtId"].split("_")[1])
            if box_type == "racial":
                non_white_pop = (
                    int(row["POP_HISP"]) + int(row["POP_BLACK"]) + int(row["POP_ASIAN"])
                )
                blk_pop = int(row["POP_BLACK"])
                hsp_pop = int(row["POP_HISP"])
                asn_pop = int(row["POP_ASIAN"])
                non_white_ratio = non_white_pop / total_pop
                blk_ratio = blk_pop / total_pop
                hsp_ratio = hsp_pop / total_pop
                asn_ratio = asn_pop / total_pop
            elif box_type == "party":
                rep_pop = int(row["PRE20_R"])
                dem_pop = int(row["PRE20_D"])
                rep_ratio = rep_pop / total_pop
                dem_ratio = (
                    dem_pop / total_pop
                )  # Should be int(row['VAP']) after gerrychain
        elif plan_type == "mmd":
            total_pop = int(row["POP"])
            graph_id = int(row["plan_no"].split("_")[1])
            if box_type == "racial":
                non_white_pop = (
                    int(row["POP_HISP"])  # Should be changed after gerrychain
                    + int(row["POP_BLACK"])  # Should be changed after gerrychain
                    + int(row["POP_ASIAN"])  # Should be changed after gerrychain
                )
                blk_pop = int(row["POP_BLACK"])  # Should be changed after gerrychain
                hsp_pop = int(row["POP_HISP"])  # Should be changed after gerrychain
                asn_pop = int(row["POP_ASIAN"])  # Should be changed after gerrychain
                non_white_ratio = non_white_pop / total_pop
                blk_ratio = blk_pop / total_pop
                hsp_ratio = hsp_pop / total_pop
                asn_ratio = asn_pop / total_pop
            elif box_type == "party":
                rep_pop = row["PRE20_R"]  # Should be changed after gerrychain
                dem_pop = row["PRE20_D"]  # Should be changed after gerrychain
                rep_ratio = rep_pop / total_pop
                dem_ratio = dem_pop / total_pop

        for k in population_percentages[plan_type_key].keys():
            if k == "blk":
                population_percentages[plan_type_key][k][graph_id].append(blk_ratio)
            elif k == "asn":
                population_percentages[plan_type_key][k][graph_id].append(asn_ratio)
            elif k == "hsp":
                population_percentages[plan_type_key][k][graph_id].append(hsp_ratio)
            elif k == "non_wht":
                population_percentages[plan_type_key][k][graph_id].append(
                    non_white_ratio
                )
            elif k == "republican":
                population_percentages[plan_type_key][k][graph_id].append(rep_ratio)
            elif k == "democratic":
                population_percentages[plan_type_key][k][graph_id].append(dem_ratio)

    population_percentages = {
        k: [sorted(inner_list) for inner_list in v]
        for k, v in population_percentages[plan_type_key].items()
    }

    for selection in population_percentages.keys():
        df = pd.DataFrame(population_percentages[selection])
        df = df.T
        population_percentages[selection] = df.values.tolist()

    population_percentages = {
        k: [sorted(inner_list) for inner_list in v]
        for k, v in population_percentages.items()
    }
    if box_type == "racial":
        box_whisker = {
            plan_type_key: {
                "blk": [],
                "hsp": [],
                "non_wht": [],
            }
        }
    elif box_type == "party":
        box_whisker = {
            plan_type_key: {
                "republican": [],
                "democratic": [],
            }
        }
    # print(population_percentages)
    for k in population_percentages.keys():
        for i in range(len(population_percentages[k])):
            district_data = population_percentages[k][i]

            # Calculate required statistics
            district_array = np.array(
                district_data
            )  # Since the round is too slow for large dataset for futrure, convert data into the numpy
            district_stats = {
                "min": np.round(np.min(district_array), 6),
                "lowerQuartile": np.round(np.percentile(district_array, 25), 6),
                "median": np.round(np.median(district_array), 6),
                "upperQuartile": np.round(np.percentile(district_array, 75), 6),
                "max": np.round(np.max(district_array), 6),
                "average": np.round(np.mean(district_array), 6),
            }
            box_whisker[plan_type_key][k].append(district_stats)

        gdf = gpd.read_file(enacted_path)
        enacted_blk_ratio_list = []
        enacted_hsp_ratio_list = []
        enacted_non_wht_ratio_list = []
        enacted_rep_ratio_list = []
        enacted_dem_ratio_list = []

        if box_type == "racial":
            for _, r in gdf.iterrows():
                district_num = int(r["DIST"].split()[2]) - 1
                enacted_blk_ratio = int(r["C_BLK20"] / r["C_TOT20"] * 10**6) / 10**6
                enacted_hsp_ratio = int(r["C_HSP20"] / r["C_TOT20"] * 10**6) / 10**6
                enacted_non_white_ratio = (
                    int(
                        (r["C_ASN20"] + r["C_BLK20"] + r["C_HSP20"])
                        / r["C_TOT20"]
                        * 10**6
                    )
                    / 10**6
                )
                enacted_non_wht_ratio_list.append(enacted_non_white_ratio)
                enacted_hsp_ratio_list.append(enacted_hsp_ratio)
                enacted_blk_ratio_list.append(enacted_blk_ratio)
        elif box_type == "party":
            gdf = gpd.read_file(enacted_vote_path)
            for _, r in gdf.iterrows():
                enacted_rep_ratio = int(r["vote_rep"] / r["total_pop"] * 10**6) / 10**6
                enacted_dem_ratio = int(r["vote_dem"] / r["total_pop"] * 10**6) / 10**6
                enacted_dem_ratio_list.append(enacted_dem_ratio)
                enacted_rep_ratio_list.append(enacted_rep_ratio)

        enacted_non_wht_ratio_list = sorted(enacted_non_wht_ratio_list)
        enacted_blk_ratio_list = sorted(enacted_blk_ratio_list)
        enacted_hsp_ratio_list = sorted(enacted_hsp_ratio_list)
        enacted_dem_ratio_list = sorted(enacted_dem_ratio_list)
        enacted_rep_ratio_list = sorted(enacted_rep_ratio_list)
        if box_type == "racial":
            for i in range(len(enacted_non_wht_ratio_list)):
                if plan_type == "smd":
                    if k == "blk":
                        box_whisker[plan_type_key][k][i]["enacted"] = (
                            enacted_blk_ratio_list[i]
                        )
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
                    elif k == "hsp":
                        box_whisker[plan_type_key][k][i]["enacted"] = (
                            enacted_hsp_ratio_list[i]
                        )
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
                    elif k == "non_wht":
                        box_whisker[plan_type_key][k][i]["enacted"] = (
                            enacted_non_wht_ratio_list[i]
                        )
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
                if plan_type == "mmd":
                    mmd_dist_num = len(box_whisker[plan_type_key][k])
                    for i in range(mmd_dist_num):
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
        elif box_type == "party":
            for i in range(len(enacted_dem_ratio_list)):
                if plan_type == "smd":
                    if k == "republican":
                        box_whisker[plan_type_key][k][i]["enacted"] = (
                            enacted_rep_ratio_list[i]
                        )
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
                    elif k == "democratic":
                        box_whisker[plan_type_key][k][i]["enacted"] = (
                            enacted_dem_ratio_list[i]
                        )
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
                if plan_type == "mmd":
                    mmd_dist_num = len(box_whisker[plan_type_key][k])
                    for i in range(mmd_dist_num):
                        box_whisker[plan_type_key][k][i]["name"] = i + 1
    return box_whisker


def seat_vote(ensemble_path, plan_type, state):
    gdf = gpd.read_file(ensemble_path)
    votingData = defaultdict(lambda: {"repVotes": 0, "demVotes": 0, "count": 0})
    votingByDistrict = []
    seatsVotesRep = []
    seatsVotesDem = []

    SWING_CONST = 0.01  # Percentage to increase each district by

    totalVotes, totalDemVotes, totalRepVotes = [0] * 3

    # Read all district data
    for i, row in gdf.iterrows():
        if plan_type == "smd":
            districtId = int(row["districtId"].split("_")[-1])
            rep = int(row["PRE20_R"])
            dem = int(row["PRE20_D"])
        elif plan_type == "mmd":
            districtId = row["mmd_dist"]
            rep = int(row["PRE20_R"])
            dem = int(row["PRE20_D"])
        total = rep + dem

        votingData[districtId]["repVotes"] += rep
        votingData[districtId]["demVotes"] += dem
        votingData[districtId]["count"] += 1

        totalVotes += total
        totalDemVotes += dem
        totalRepVotes += rep
        percentRep = float(rep) / total

    # votingByDistrict.append({"percentRep": percentRep, "percentDem": 1.0 - percentRep})

    # -   - - - -- - --  - - - -
    for districtId, data in votingData.items():
        avgRep = data["repVotes"] / data["count"]
        avgDem = data["demVotes"] / data["count"]
        total = avgRep + avgDem
        percentRep = avgRep / total
        percentDem = avgDem / total
        votingByDistrict.append({"percentRep": percentRep, "percentDem": percentDem})
    # - - - - - - - - - -  -- - - -
    repVoteShare = float(totalRepVotes) / totalVotes
    demVoteShare = float(totalDemVotes) / totalVotes

    # print(repVoteShare, demVoteShare)

    diff = (((100 * repVoteShare) % 1) - ((100 * demVoteShare) % 1)) / 100
    # print(diff)
    # print(votingByDistrict)
    # print(votingData)
    # Generate curve
    i = repVoteShare
    counter = 0
    while i <= 1:
        totalRepSeats = 0
        totalDemSeats = 0
        for j in range(0, 1000):  # simulate 1000 elections

            districtOverflowedRep = [False] * len(votingByDistrict)
            districtOverflowedDem = [False] * len(votingByDistrict)
            excessDem = 0
            excessRep = 0
            updatedValsRep = [0] * len(votingByDistrict)
            updatedValsDem = [0] * len(votingByDistrict)

            for k, district in enumerate(votingByDistrict):
                updatedValsRep[k] = (
                    district["percentRep"]
                    + counter * SWING_CONST
                    + SWING_CONST * random.randint(-5, 5)
                )
                updatedValsDem[k] = 1 - updatedValsRep[k] + diff

                if updatedValsRep[k] > 1:
                    excessRep += 1
                    districtOverflowedRep[k] = True

                if updatedValsDem[k] < 0:
                    excessDem += 1
                    districtOverflowedDem[k] = True

            for k, district in enumerate(votingByDistrict):
                # Overflow mechanic: distribute excess votes to the other districts
                if districtOverflowedRep[k] is False:
                    updatedValsRep[k] += SWING_CONST * (
                        excessRep / (len(votingByDistrict) - excessRep)
                    )

                if districtOverflowedDem[k] is False:
                    updatedValsDem[k] -= SWING_CONST * (
                        excessDem / (len(votingByDistrict) - excessDem)
                    )

                if updatedValsRep[k] > 0.50:
                    totalRepSeats += 1
                if updatedValsDem[k] > 0.50:
                    totalDemSeats += 1

        i += SWING_CONST
        counter += 1

        if i <= 1:
            seatsVotesRep.append(
                {
                    "seats": float(totalRepSeats) / (len(votingByDistrict) * 1000.0),
                    "votes": i,
                }
            )
            seatsVotesDem.insert(
                0,
                {
                    "seats": float(totalDemSeats) / (len(votingByDistrict) * 1000.0),
                    "votes": 1 - i + diff,
                },
            )

    i = demVoteShare
    counter = 0

    while i <= 1:
        totalDemSeats = 0
        totalRepSeats = 0

        for j in range(0, 1000):  # simulate 1000 elections
            districtOverflowedRep = [False] * len(votingByDistrict)
            districtOverflowedDem = [False] * len(votingByDistrict)
            excessDem = 0
            excessRep = 0
            updatedValsRep = [0] * len(votingByDistrict)
            updatedValsDem = [0] * len(votingByDistrict)

            for k, district in enumerate(votingByDistrict):
                updatedValsDem[k] = (
                    district["percentDem"]
                    + counter * SWING_CONST
                    + SWING_CONST * random.randint(-5, 5)
                    + diff
                )
                updatedValsRep[k] = 1 - (updatedValsDem[k] - diff)

                if updatedValsRep[k] > 1:
                    excessRep += 1
                    districtOverflowedRep[k] = True

                if updatedValsDem[k] < 0:
                    excessDem += 1
                    districtOverflowedDem[k] = True

            for k, district in enumerate(votingByDistrict):
                # Overflow mechanic: distribute excess votes to the other districts
                if districtOverflowedRep[k] is False:
                    updatedValsRep[k] -= SWING_CONST * (
                        excessRep / (len(votingByDistrict) - excessRep)
                    )

                if districtOverflowedDem[k] is False:
                    updatedValsDem[k] += SWING_CONST * (
                        excessDem / (len(votingByDistrict) - excessDem)
                    )

                if updatedValsRep[k] > 0.50:
                    totalRepSeats += 1
                if updatedValsDem[k] > 0.50:
                    totalDemSeats += 1

        i += SWING_CONST
        counter += 1

        if i <= 1:
            seatsVotesDem.append(
                {
                    "seats": float(totalDemSeats) / (len(votingByDistrict) * 1000.0),
                    "votes": i + diff,
                }
            )
            seatsVotesRep.insert(
                0,
                {
                    "seats": float(totalRepSeats) / (len(votingByDistrict) * 1000.0),
                    "votes": 1 - i,
                },
            )

    # Add endpoints
    seatsVotesRep.insert(0, {"seats": 0, "votes": 0})
    seatsVotesDem.insert(0, {"seats": 0, "votes": 0})
    seatsVotesRep.append({"seats": 1, "votes": 1})
    seatsVotesDem.append({"seats": 1, "votes": 1})

    d = {plan_type: []}
    # Add seat-vote data for Republicans and Democrats
    for i in range(1, len(seatsVotesRep)):
        d[plan_type].append(
            {
                "republican": int(100 * seatsVotesRep[i]["seats"] * 10**6) / 10**6,
                "democratic": int(100 * seatsVotesDem[i]["seats"] * 10**6) / 10**6,
            }
        )
    d["bias"] = (
        int((d[plan_type][len(d[plan_type]) // 2]["republican"] - 50) * 10**3) / 10**3
    )

    d["symmetry"] = 0.0
    vote_range = d[plan_type][44:55]
    differences = [abs(y["republican"] - y["democratic"]) for y in vote_range]
    partisan_symmetry = round(sum(differences) / len(differences), 3)
    d["symmetry"] = partisan_symmetry

    rep_seat_share = [y["republican"] for y in vote_range]
    dem_seat_share = [y["democratic"] for y in vote_range]
    rep_slopes = [
        (rep_seat_share[i + 1] - rep_seat_share[i]) / 1
        for i in range(len(rep_seat_share) - 1)
    ]
    dem_slopes = [
        (dem_seat_share[i + 1] - dem_seat_share[i]) / 1
        for i in range(len(dem_seat_share) - 1)
    ]

    d["responsiveness"] = {}
    rep_responsiveness = round(sum(rep_slopes) / len(rep_slopes), 3)
    dem_responsiveness = round(sum(dem_slopes) / len(dem_slopes), 3)
    d["responsiveness"]["republican"] = rep_responsiveness
    d["responsiveness"]["democratic"] = dem_responsiveness

    return d


def summary(ensemble_path, plan_type, state):
    summary = {
        "num_dist_plan": 0,
        "avg_min_max_diff": 0.0,
        "layout": [],
    }
    avg_min_max_diff = 0.0
    plan_populations = {}

    gdf = gpd.read_file(ensemble_path)
    for _, row in gdf.iterrows():
        if plan_type == "smd":
            graph_id = int(row["districtId"].split("_")[1])
            district_population = int(row["POP"])

            if graph_id not in plan_populations:
                plan_populations[graph_id] = []
            plan_populations[graph_id].append(district_population)
        elif plan_type == "mmd":
            graph_id = int(row["plan_no"].split("_")[1])
            district_population = int(row["POP"])

            if graph_id not in plan_populations:
                plan_populations[graph_id] = []
            plan_populations[graph_id].append(district_population)

    min_max_diff_list = []
    for _, populations in plan_populations.items():
        min_pop = min(populations)
        max_pop = max(populations)
        min_max_diff = max_pop - min_pop
        min_max_diff_list.append(min_max_diff)
    print(plan_populations)
    print((min_max_diff_list))
    if sum(min_max_diff_list) > 0:
        avg_min_max_diff = round(
            (max(min_max_diff_list) - min(min_max_diff_list)) / sum(min_max_diff_list),
            3,
        )
    else:
        avg_min_max_diff = 0.0
    summary["avg_min_max_diff"] = avg_min_max_diff
    if plan_type == "smd":
        del summary["layout"]
    if plan_type == "smd" and state == "MS":
        district_num_by_state = 4
    elif plan_type == "smd" and state == "AL":
        district_num_by_state = 7
    elif plan_type == "smd" and state == "PA":
        district_num_by_state = 18
    elif plan_type == "mmd" and state == "MS":
        district_num_by_state = 1
        summary["layout"] = [4]
    elif plan_type == "mmd" and state == "AL":
        district_num_by_state = 2
        summary["layout"] = [3, 4]
    elif plan_type == "mmd" and state == "PA":
        district_num_by_state = 4
        summary["layout"] = [5, 5, 5, 3]
    total_plan_num = len(gdf) / district_num_by_state
    summary["num_dist_plan"] = int(total_plan_num)
    return summary


# "enacted_vs_avg": {
#             "enacted": {
#                 "republican": 5,
#                 "democratic": 2,
#                 "num_op_representatives": 2,
#                 "seats-votes": [
#                     {"republican": 0.0, "democratic": 0.0},
#                     {"republican": 0.0, "democratic": 0.0},
#                 ],
#             },
#             "avg_mmd": {
#                 "republican": 0.456,
#                 "democratic": 0.544,
#                 "num_op_representatives": 1.2,
#                 "seats-votes": [  # AVG => div by 5
#                     {"republican": 0.0, "democratic": 0.0},
#                     {"republican": 0.0, "democratic": 0.0},
#                 ],
#             },
#         },
def enacted_vs_mmd(enacted_path, mmd_ensemble_path, state):
    enacted_vs_avg = (
        {
            "enacted": {
                "republican": 0,
                "democratic": 0,
                "num_op_representatives": 2,
                "seats_votes": [
                    {"republican": 0.0, "democratic": 0.0},
                    {"republican": 0.0, "democratic": 0.0},
                ],
            },
            "avg_mmd": {
                "republican": 0.0,
                "democratic": 0.0,
                "num_op_representatives": 0.0,
                "seats_votes": [  # AVG => div by 5000
                    {"republican": 0.0, "democratic": 0.0},
                    {"republican": 0.0, "democratic": 0.0},
                ],
            },
        },
    )
    enacted = gpd.read_file(enacted_path)
    mmd = gpd.read_file(mmd_ensemble_path)
    for _, r in enacted.iterrows():
        if r["win_pty"] == "REPUBLICANS":
            enacted_vs_avg["enacted"]["republican"] += 1
        elif r["win_pty"] == "DEMOCRATS":
            enacted_vs_avg["enacted"]["democratic"] += 1
        if (int(r["total_asn"]) + int(r["total_blk"]) + int(r["total_hsp"])) / int(
            r["total"]
        ) > 0.5:
            enacted_vs_avg["enacted"]["num_op_representatives"] += 1
    enacted_vs_avg["enacted"]["seats_votes"] = seat_vote(enacted_path, "smd", state)[
        "smd"
    ]
    for _, r in mmd.iterrows():
        r[""]  # Should be changed..


if __name__ == "__main__":
    states = ["MS", "AL", "PA"]
    for state in states:
        state_lower = state.lower()
        racial_smd_box = box_whisker(
            f"./Sample_Ensemble_5/{state}_smd_ensemble.geojson",
            f"./district_data/{state_lower}_cvap_2020_cd/",
            f"./2020_election/{state}/{state}_dist_election_comb.geojson",  # smd election
            "smd",
            5,
            "racial",
        )
        party_smd_box = box_whisker(
            f"./Sample_Ensemble_5/{state}_smd_ensemble.geojson",
            f"./district_data/{state_lower}_cvap_2020_cd/",
            f"./2020_election/{state}/{state}_dist_election_comb.geojson",
            "smd",
            5,
            "party",
        )
        racial_mmd_box = box_whisker(
            f"./Sample_Ensemble_5/{state}_mmd_ensemble.geojson",
            f"./district_data/{state_lower}_cvap_2020_cd/",
            f"./2020_election/{state}/{state}_dist_election_comb.geojson",  # smd election
            "mmd",
            5,
            "racial",
        )
        party_mmd_box = box_whisker(
            f"./Sample_Ensemble_5/{state}_mmd_ensemble.geojson",
            f"./district_data/{state_lower}_cvap_2020_cd/",
            f"./2020_election/{state}/{state}_dist_election_comb.geojson",
            "mmd",
            5,
            "party",
        )

        smd_seat_vote_curve = seat_vote(
            f"./Sample_Ensemble_5/{state}_smd_ensemble.geojson", "smd", state
        )
        mmd_seat_vote_curve = seat_vote(
            f"./Sample_Ensemble_5/{state}_mmd_ensemble.geojson", "mmd", state
        )
        smd_party_split = party_split(
            f"./Sample_Ensemble_5/{state}_smd_ensemble.geojson", "smd", state
        )
        mmd_party_split = party_split(
            f"./Sample_Ensemble_5/{state}_mmd_ensemble.geojson", "mmd", state
        )
        smd_op_bar = op_bar(f"./Sample_Ensemble_5/{state}_smd_ensemble.geojson", "smd")
        mmd_op_bar = op_bar(f"./Sample_Ensemble_5/{state}_mmd_ensemble.geojson", "mmd")

        smd_summary = summary(
            f"./Sample_Ensemble_5/{state}_smd_ensemble.geojson", "smd", state
        )
        mmd_summary = summary(
            f"./Sample_Ensemble_5/{state}_mmd_ensemble.geojson", "mmd", state
        )

        data_format["state"] = state
        if state == "PA":
            data_format["mmd"]["summary"]["layout"] = [5, 5, 5, 3]
        elif state == "MS":
            data_format["mmd"]["summary"]["layout"] = [4]
        elif state == "AL":
            data_format["mmd"]["summary"]["layout"] = [3, 4]
        data_format["smd"]["racial"]["racial_box_whisker"] = racial_smd_box["racial"]
        data_format["smd"]["party"]["party_box_whisker"] = party_smd_box["party"]
        data_format["mmd"]["racial"]["racial_box_whisker"] = racial_mmd_box["racial"]
        data_format["mmd"]["party"]["party_box_whisker"] = party_mmd_box["party"]

        data_format["smd"]["summary"]["seats_votes"] = smd_seat_vote_curve["smd"]
        data_format["smd"]["summary"]["bias"] = smd_seat_vote_curve["bias"]
        data_format["smd"]["summary"]["symmetry"] = smd_seat_vote_curve["symmetry"]
        data_format["smd"]["summary"]["responsiveness"] = smd_seat_vote_curve[
            "responsiveness"
        ]
        data_format["mmd"]["summary"]["seats_votes"] = mmd_seat_vote_curve["mmd"]
        data_format["mmd"]["summary"]["bias"] = mmd_seat_vote_curve["bias"]
        data_format["mmd"]["summary"]["symmetry"] = mmd_seat_vote_curve["symmetry"]
        data_format["mmd"]["summary"]["responsiveness"] = mmd_seat_vote_curve[
            "responsiveness"
        ]

        data_format["smd"]["racial"]["op_districts"] = smd_op_bar["op_district_bar"][
            "smd"
        ]
        data_format["mmd"]["racial"]["op_districts"] = mmd_op_bar["op_district_bar"][
            "mmd"
        ]

        data_format["smd"]["racial"]["op_representatives"] = smd_op_bar[
            "op_representatives_bar"
        ]["smd"]
        data_format["mmd"]["racial"]["op_representatives"] = mmd_op_bar[
            "op_representatives_bar"
        ]["mmd"]

        data_format["smd"]["party"]["party_splits"] = smd_party_split[
            "party_splits_bar"
        ]["smd"]
        data_format["mmd"]["party"]["party_splits"] = mmd_party_split[
            "party_splits_bar"
        ]["mmd"]

        data_format["smd"]["summary"]["avg_party_split"]["republican"] = (
            smd_party_split["avg_rep"]
        )
        data_format["mmd"]["summary"]["avg_party_split"]["democratic"] = (
            mmd_party_split["avg_dem"]
        )
        data_format["smd"]["summary"]["avg_num_minor_representatives"]["non_wht"] = (
            smd_op_bar["avg_op_repre"]
        )
        data_format["mmd"]["summary"]["avg_num_minor_representatives"]["non_wht"] = (
            mmd_op_bar["avg_op_repre"]
        )

        data_format["smd"]["summary"]["num_dist_plan"] = smd_summary["num_dist_plan"]
        data_format["smd"]["summary"]["avg_min_max_diff"] = smd_summary[
            "avg_min_max_diff"
        ]

        data_format["mmd"]["summary"]["num_dist_plan"] = mmd_summary["num_dist_plan"]
        data_format["mmd"]["summary"]["avg_min_max_diff"] = mmd_summary[
            "avg_min_max_diff"
        ]

        with open(f"./Sample_Ensemble_5/{state}_ensemble.json", "w") as f:
            json.dump(data_format, f, indent=2)
            f.close()
        print(f"{state} post_process done..")
