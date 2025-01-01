import json, geopandas as gpd, pandas as pd
from shapely.geometry import mapping
from math import prod
from common import *


def calculate_smd_data(row: gpd.GeoDataFrame):
    d = {}
    d["districtId"] = row["districtId"]
    d["totalPopulation"] = int(row["POP"])
    d["votePopulation"] = int(row["VAP"])
    d["totalAsian"] = int(row["POP_ASIAN"])
    d["totalBlack"] = int(row["POP_BLACK"])
    d["totalHispanic"] = int(row["POP_HISP"])
    d["totalWhite"] = int(row["POP_WHITE"])
    d["democraticVotes"] = int(row["PRE20_D"])
    d["republicanVotes"] = int(row["PRE20_R"])
    d["totalVotes"] = int(row["PRE20_D"]) + int(row["PRE20_R"])
    d["winningParty"] = (
        "republican" if d["republicanVotes"] > d["democraticVotes"] else "democratic"
    )
    # centroid = row["geometry"].centroid
    centroid = row["geometry"].representative_point()
    d["centroid"] = f"{centroid.x},{centroid.y}"
    d["geometry"] = row["geometry"]
    return d


def calculate_mmd_data(row: gpd.GeoDataFrame, state: str):
    d = {}
    d["districtId"] = row["plan_no"]
    d["num_dist"] = row["num_dist"]
    d["totalPopulation"] = int(row["POP"])
    d["votePopulation"] = int(row["VAP"])
    d["totalAsian"] = int(row["POP_ASIAN"])
    d["totalBlack"] = int(row["POP_BLACK"])
    d["totalHispanic"] = int(row["POP_HISP"])
    d["totalWhite"] = int(row["POP_WHITE"])
    election_res = mmd_election(
        f"./dataset/Gerrychain/{state}_mmd_elec.csv",
        d["districtId"],
        row["mmd_dist"],
        d["num_dist"],
    )
    d["RepublicanWins"] = election_res["rep_wins"]
    d["DemocraticWins"] = election_res["dem_wins"]
    # [('D_0', 312567), ('R_0', 307934), ('R_1', 281045), ('D_2', 269919)]
    vote_dem, vote_rep, winners, winners_vote = 0, 0, [], []
    for candidate, vote in election_res["elected_candidates"]:
        if candidate.startswith("R_"):
            if vote > 0:
                winners.append(candidate)
                vote_rep += vote
            winners_vote.append(str(vote))
        elif candidate.startswith("D_"):
            if vote > 0:
                winners.append(candidate)
                vote_dem += vote
                winners_vote.append(str(vote))
    d["democraticVotes"] = int(vote_dem)
    d["republicanVotes"] = int(vote_rep)
    d["totalVotes"] = int(vote_dem) + int(vote_rep)
    winners = ",".join(winners)
    winners_vote = ",".join(winners_vote)
    d["winningParty"] = winners
    d["winningPartyVotes"] = winners_vote
    d["opportunityThreshold"] = set_op_threshold(row["num_dist"])
    # centroid = row["geometry"].centroid
    centroid = row["geometry"].representative_point()
    d["centroid"] = f"{centroid.x},{centroid.y}"
    d["geometry"] = row["geometry"]
    return d


def insert_election_res_to_ensemble(ensemble_path: str, plan_type: str, state: str):
    gdf = gpd.read_file(ensemble_path)
    data = []
    for _, row in gdf.iterrows():
        if plan_type == "smd":
            data.append(calculate_smd_data(row))
        elif plan_type == "mmd":
            data.append(calculate_mmd_data(row, state))
    df = pd.DataFrame(data)
    df = df.fillna(0)
    geo = gpd.GeoDataFrame(df, geometry="geometry")
    geo = geo.set_crs("EPSG:4269")
    return geo


def initialize_dict():
    return {"rep": 0, "dem": 0, "op_max": 0, "non_wht_prob_max": 0, "wht_prob_max": 0}


def calculate_plan_metrics(group: gpd.GeoDataFrame, plan_type: str):
    total_pop, num_op_dist, rep_win, dep_win = 0, 0, 0, 0
    district_non_wht_prob, district_wht_prob = [], []

    for _, row in group.iterrows():
        op_threshold = 0.5 if plan_type == "smd" else set_op_threshold(row["num_dist"])
        district_non_wht = row["totalHispanic"] + row["totalBlack"] + row["totalAsian"]
        district_wht = row["totalWhite"]
        district_pop = row["totalPopulation"]
        total_pop += row["totalPopulation"]
        if plan_type == "smd":
            if row["republicanVotes"] > row["democraticVotes"]:
                rep_win += 1
            else:
                dep_win += 1
        elif plan_type == "mmd":
            winner_list = row["winningParty"].split(",")
            for winner in winner_list:
                if winner.startswith("R"):
                    rep_win += 1
                elif winner.startswith("D"):
                    dep_win += 1
        district_non_wht_prob.append(district_non_wht / district_pop)
        district_wht_prob.append(district_wht / district_pop)
        for race_pop in [
            row["totalAsian"],
            row["totalBlack"],
            row["totalHispanic"],
        ]:
            if race_pop / district_pop > op_threshold:
                num_op_dist += 1
                break

    return {
        "rep_seat": rep_win,
        "dem_seat": dep_win,
        "num_op_dist": num_op_dist,
        "non_wht_prob": (prod(district_non_wht_prob) * 100),
        "wht_prob": (prod(district_wht_prob) * 100),
    }


def update_plan_metrics(metrics: dict, compare: dict, reason_dict: dict, graph_id: str):
    if metrics["rep_seat"] > compare["rep"]:
        compare["rep"] = metrics["rep_seat"]
        reason_dict["rep"] = graph_id
    if metrics["dem_seat"] > compare["dem"]:
        compare["dem"] = metrics["dem_seat"]
        reason_dict["dem"] = graph_id
    if metrics["num_op_dist"] > compare["op_max"]:
        compare["op_max"] = metrics["num_op_dist"]
        reason_dict["op_max"] = graph_id
    if metrics["non_wht_prob"] > compare["non_wht_prob_max"]:
        compare["non_wht_prob_max"] = metrics["non_wht_prob"]
        reason_dict["non_wht_prob_max"] = graph_id
    if metrics["wht_prob"] > compare["wht_prob_max"]:
        compare["wht_prob_max"] = metrics["wht_prob"]
        reason_dict["wht_prob_max"] = graph_id


def select_interesting_plans(inserted_election_emsemble_path: str, plan_type: str):
    gdf = gpd.read_file(inserted_election_emsemble_path)
    smd_reason_plan_dict, smd_compare = initialize_dict(), initialize_dict()
    mmd_reason_plan_dict, mmd_compare = initialize_dict(), initialize_dict()
    grouped_plan = gdf.groupby(gdf["districtId"].str.split("_").str[1])
    for _, single_plan in grouped_plan:
        graph_id = (
            single_plan["districtId"].apply(lambda x: x.split("_")[1]).unique()[0]
        )
        metrics = calculate_plan_metrics(single_plan, plan_type)
        if plan_type == "smd":
            update_plan_metrics(metrics, smd_compare, smd_reason_plan_dict, graph_id)
        elif plan_type == "mmd":
            update_plan_metrics(metrics, mmd_compare, mmd_reason_plan_dict, graph_id)
    return (
        (smd_reason_plan_dict, smd_compare)
        if plan_type == "smd"
        else (mmd_reason_plan_dict, mmd_compare)
    )


def create_feature(row: gpd.GeoDataFrame):
    properties = {
        "totalPopulation": row["totalPopulation"],
        "votePopulation": row["votePopulation"],
        "totalAsian": row["totalAsian"],
        "totalBlack": row["totalBlack"],
        "totalHispanic": row["totalHispanic"],
        "totalWhite": row["totalWhite"],
        "democraticVotes": row["democraticVotes"],
        "republicanVotes": row["republicanVotes"],
        "winningParty": row["winningParty"],
        "centroid": f"{row.geometry.representative_point().x},{row.geometry.representative_point().y}",
        # "centroid": f"{row.geometry.centroid.x},{row.geometry.centroid.y}",
    }

    if "num_dist" in row:  # MMD case!
        properties["winningPartyVotes"] = row["winningPartyVotes"]
        properties["districtNumber"] = row["num_dist"]
        properties["opportunityThreshold"] = row["opportunityThreshold"]

    return {
        "type": "Feature",
        "districtId": row["districtId"],
        "properties": properties,
        "geometry": mapping(row["geometry"]),
    }


def calculate_totals(row: gpd.GeoDataFrame, totals: dict):
    totals["totalPopulation"] += row["totalPopulation"]
    totals["votePopulation"] += row["votePopulation"]
    totals["totalBlack"] += row["totalBlack"]
    totals["totalHispanic"] += row["totalHispanic"]
    totals["totalAsian"] += row["totalAsian"]
    totals["totalWhite"] += row["totalWhite"]
    totals["totalNonWhite"] += (
        row["totalBlack"] + row["totalHispanic"] + row["totalAsian"]
    )


def calculate_op_district_and_select_from_graph_id_dict(
    geojson_path: str, plan_type: str, state: str, graph_id_dict: dict
):
    gdf = gpd.read_file(geojson_path)
    reason_with_id = graph_id_dict
    res = []
    for k, v in reason_with_id.items():
        matched_plan = gdf[gdf["districtId"].apply(lambda x: x.split("_")[1]) == str(v)]
        seat_vote_curve = random_seat_vote(matched_plan, plan_type)
        plan = initialize_plan(state, plan_type, k)
        totals = initialize_totals()
        dict_for_prob = calculate_plan_metrics(matched_plan, p)
        plan["nonWhiteProbability"] = float(f'{dict_for_prob["non_wht_prob"]:.3e}')
        plan["whiteProbability"] = float(f'{dict_for_prob["wht_prob"]:.3e}')
        for _, row in matched_plan.iterrows():
            process_district_row(row, plan, totals, plan_type)
        finalize_plan(plan, totals, seat_vote_curve, plan_type)
        res.append(plan)
    with open(
        f"./dataset/District/Random_district/{state}_{plan_type}_random_plan.json", "w"
    ) as f:
        json.dump(res, f, indent=2)
        print(
            f"selection ./random_district_plan/{state}_{plan_type}_random_plan.json done...\n"
        )


def initialize_plan(state: str, plan_type: str, reason: str):
    num_district = {
        "smd": {"MS": 4, "AL": 7, "PA": 18},
        "mmd": {"MS": 1, "AL": 2, "PA": 4},
    }
    return {
        "state": state,
        "districtType": plan_type,
        "totalPopulation": 0,
        "votePopulation": 0,
        "totalAsian": 0,
        "totalBlack": 0,
        "totalHispanic": 0,
        "totalWhite": 0,
        "nonWhiteProbability": 0,
        "whiteProbability": 0,
        "republican": 0,
        "democratic": 0,
        "numOpportunityDistricts": 0,
        "opportunityThreshold": 0.0,
        "numSafeDistricts": 0,
        "totalDistricts": num_district[plan_type][state],
        "reason": reason,
        "seatsVotes": [],
        "bias": 0,
        "symmetry": 0,
        "responsiveness": {"republican": 0, "democratic": 0},
        "features": [],
    }


def initialize_totals():
    return {
        "totalPopulation": 0,
        "votePopulation": 0,
        "totalBlack": 0,
        "totalHispanic": 0,
        "totalAsian": 0,
        "totalWhite": 0,
        "totalNonWhite": 0,
    }


def finalize_plan(plan: dict, totals: dict, seat_vote_curve: dict, plan_type: str):
    plan.update(
        {
            "totalPopulation": totals["totalPopulation"],
            "votePopulation": totals["votePopulation"],
            "totalBlack": totals["totalBlack"],
            "totalHispanic": totals["totalHispanic"],
            "totalAsian": totals["totalAsian"],
            "totalWhite": totals["totalWhite"],
            "seatsVotes": seat_vote_curve[plan_type],
            "bias": seat_vote_curve["bias"],
            "symmetry": seat_vote_curve["symmetry"],
            "responsiveness": seat_vote_curve["responsiveness"],
        }
    )


def process_district_row(
    row: gpd.GeoDataFrame, plan: str, totals: dict, plan_type: str
):
    calculate_totals(row, totals)
    plan["features"].append(create_feature(row))

    non_white_ratio = (
        row["totalBlack"] + row["totalHispanic"] + row["totalAsian"]
    ) / row["totalPopulation"]

    if plan_type == "smd":
        update_smd_plan(row, plan, non_white_ratio)
    elif plan_type == "mmd":
        update_mmd_plan(row, plan, non_white_ratio)


def update_smd_plan(row: gpd.GeoDataFrame, plan: str, non_white_ratio: float):
    plan["opportunityThreshold"] = 0.5
    vote_ratio_diff = abs(
        row["republicanVotes"] / row["totalVotes"]
        - row["democraticVotes"] / row["totalVotes"]
    )
    if row["winningParty"] == "republican":
        plan["republican"] += 1
    elif row["winningParty"] == "democratic":
        plan["democratic"] += 1
    if non_white_ratio > 0.5:
        plan["numOpportunityDistricts"] += 1
    if vote_ratio_diff > 0.1:
        plan["numSafeDistricts"] += 1


def update_mmd_plan(row: gpd.GeoDataFrame, plan: str, non_white_ratio: float):
    plan.pop("opportunityThreshold", None)
    winner_list = row["winningParty"].split(",")
    vote_ratio_diff = abs(
        row["republicanVotes"] / row["totalVotes"]
        - row["democraticVotes"] / row["totalVotes"]
    )
    for winner in winner_list:
        if winner.startswith("R"):
            plan["republican"] += 1
        elif winner.startswith("D"):
            plan["democratic"] += 1
    if non_white_ratio > set_op_threshold(row["num_dist"]):
        plan["numOpportunityDistricts"] += 1
    if vote_ratio_diff > 0.1:
        plan["numSafeDistricts"] += 1


if __name__ == "__main__":
    state = ["MS", "AL", "PA"]
    plan = ["smd", "mmd"]
    for s in state:
        for p in plan:

            insert_election_res_to_ensemble(
                f"./dataset/5000_ensemble/{s}_{p}_ensemble.geojson", p, s
            ).to_file(
                f"./dataset/District/Random_district/{s}_{p}_ensemble.geojson",
                driver="GeoJSON",
            )

            intersting_plan_dict, compare_dict = select_interesting_plans(
                f"./dataset/District/Random_district/{s}_{p}_ensemble.geojson", p
            )
            print(intersting_plan_dict, compare_dict)

            calculate_op_district_and_select_from_graph_id_dict(
                f"./dataset/District/Random_district/{s}_{p}_ensemble.geojson",
                p,
                s,
                intersting_plan_dict,
            )
