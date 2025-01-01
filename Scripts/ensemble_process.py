import json, geopandas as gpd, numpy as np, pandas as pd
from common import *

data_format = {
    "state": "",
    "smd": {
        "summary": {
            "numDistrictPlan": 0,
            "averageMinMaxDifference": 0.0,  # percentage
            "averageNonWhiteRepresentatives": 0.0,  # float
            "averagePartySplit": {
                "republican": 0.0,  # float
                "democratic": 0.0,
            },  # Total # of rep + dem repre / 5000
            "seatsVotes": [],
            "bias": 0.0,  # percentage
            "symmetry": 0.0,  # No unit
            # No unit
            "responsiveness": {"republican": 0.0, "democratic": 0.0},
        },
        "racial": {
            "racialBoxWhisker": {
                "black": [],
                "hispanic": [],
                "asian": [],
                "nonWhite": [],
            },
            "opportunityDistricts": {
                "black": [],
                "hispanic": [],
                "asian": [],
                "nonWhite": [],
            },
            "opportunityRepresentatives": {
                "black": [],
                "hispanic": [],
                "asian": [],
                "nonWhite": [],
            },
        },
        "party": {
            "averageSeatShare": {"republican": 0, "democratic": 0},
            "partyBoxWhisker": {
                "republican": [],
                "democratic": [],
            },
            "partySplits": [],
        },
    },
    "mmd": {
        "summary": {
            "numDistrictPlan": 0,
            "averageMinMaxDifference": 0.0,  # percentage
            "averageNonWhiteRepresentatives": 0.0,  # float
            "averagePartySplit": {
                "republican": 0.0,  # float
                "democratic": 0.0,
            },  # Total # of rep + dem repre / 5000
            "seatsVotes": [],
            "bias": 0.0,  # percentage
            "symmetry": 0.0,  # No unit
            # No unit
            "responsiveness": {"republican": 0.0, "democratic": 0.0},
            "layout": [],
        },
        "racial": {
            "racialBoxWhisker": {
                "black": [],
                "hispanic": [],
                "asian": [],
                "nonWhite": [],
            },
            "opportunityDistricts": {
                "black": [],
                "hispanic": [],
                "asian": [],
                "nonWhite": [],
            },
            "opportunityRepresentatives": {
                "black": [],
                "hispanic": [],
                "asian": [],
                "nonWhite": [],
            },
        },
        "party": {
            "partyBoxWhisker": {
                "republican": [],
                "democratic": [],
            },
            "partySplits": [],
        },
        "enactedAverage": {
            "enacted": {
                "republican": 0,
                "democratic": 0,
                "numOpportunityRepresentatives": 0,
                "seatsVotes": [],
            },
            "averageMmd": {
                "republican": 0,
                "democratic": 0,
                "numOpportunityRepresentatives": 0,
                "seatsVotes": [],
            },
        },
    },
}
avg_mmd_party_cand = set()
avg_mmd_op_cand = set()


def update_op_counts(population_ratios: dict, op_counts: dict, op_threshold: float):
    for key, ratio in population_ratios.items():
        if ratio > op_threshold:
            op_counts[key]["district"] += 1
            op_counts[key]["representative"] += int(ratio // op_threshold)


def get_population_ratios(row: gpd.GeoDataFrame):
    total_pop = int(row["POP"])
    pop_ratios = {
        "black": int(row["POP_BLACK"]) / total_pop,
        "hispanic": int(row["POP_HISP"]) / total_pop,
        "asian": int(row["POP_ASIAN"]) / total_pop,
        "nonWhite": (
            int(row["POP_BLACK"]) + int(row["POP_HISP"]) + int(row["POP_ASIAN"])
        )
        / total_pop,
    }
    return pop_ratios


def create_op_bar(ensemble_path: str, plan_type: str):  # Main function
    op_bar = {
        "op_district_bar": {
            race: [] for race in ["black", "hispanic", "asian", "nonWhite"]
        },
        "op_representatives_bar": {
            race: [] for race in ["black", "hispanic", "asian", "nonWhite"]
        },
    }
    gdf = gpd.read_file(ensemble_path)
    grouped_plan, _ = group_by_plan_type(gdf, plan_type, state)
    total_non_wht_op_repre = 0
    for _, single_plan in grouped_plan:
        op_counts = {
            race: {"district": 0, "representative": 0}
            for race in ["black", "hispanic", "asian", "nonWhite"]
        }
        for _, row in single_plan.iterrows():
            population_ratios = get_population_ratios(row)
            op_threshold = (
                0.5 if plan_type == "smd" else set_op_threshold(row["num_dist"])
            )
            update_op_counts(population_ratios, op_counts, op_threshold)
        for key in op_counts:
            op_num = {"MS": 1, "AL": 2, "PA": 4}
            if (
                plan_type == "mmd"
                and key == "nonWhite"
                and op_counts[key]["district"] == op_num[state]
            ):
                avg_mmd_op_cand.add(row["plan_no"])
            if plan_type == "mmd":
                population_ratios["nonWhite"] * row["num_dist"]
            op_bar["op_district_bar"][key].append(
                {"numOpDistricts": op_counts[key]["district"]}
            )
            op_bar["op_representatives_bar"][key].append(
                {"numOpRepresentatives": op_counts[key]["representative"]}
            )
        total_non_wht_op_repre += op_counts["nonWhite"]["representative"]

    op_bar["avg_op_repre"] = (
        int(total_non_wht_op_repre / len(grouped_plan) * 10**3) / 10**3
    )
    return op_bar


def create_party_split(
    ensemble_path: str, plan_type: str, state: str, mmd_csv=None
):  # Main function
    party_bar = {"party_splits_bar": []}
    gdf = gpd.read_file(ensemble_path)
    grouped_plan, _ = group_by_plan_type(gdf, plan_type, state)
    num_districts = {"MS": 4, "AL": 7, "PA": 18}.get(state, 0)
    median = {"MS": "2:2", "AL": "4:3", "PA": "9:9"}.get(state, 0)
    for i in range(num_districts + 1):
        party_bar["party_splits_bar"].append(
            {"name": f"{i}:{num_districts - i}", "value": 0}
        )
    grouped_results = grouped_plan.apply(
        lambda group: compute_party_counts(group, plan_type, mmd_csv)
    ).reset_index()
    for _, row in grouped_results.iterrows():
        rep_count = row["republican"]
        dem_count = row["democratic"]
        split_name = f"{rep_count}:{dem_count}"
        if split_name == median and plan_type == "mmd":
            avg_mmd_party_cand.add("_".join([state, row["plan_no"], "mmd"]))
        for split in party_bar["party_splits_bar"]:
            if split["name"] == split_name:
                split["value"] += 1
    total_republican = grouped_results["republican"].sum()
    total_democratic = grouped_results["democratic"].sum()
    party_bar["avg_rep"] = round(total_republican / len(grouped_plan), 3)
    party_bar["avg_dem"] = round(total_democratic / len(grouped_plan), 3)

    return party_bar


def compute_party_counts(group: gpd.GeoDataFrame, plan_type: str, mmd_csv=None):
    republican_wins = 0
    democratic_wins = 0
    if plan_type == "smd":
        republican_wins = (group["PRE20_R"] > group["PRE20_D"]).sum()
        democratic_wins = (group["PRE20_R"] < group["PRE20_D"]).sum()
    elif plan_type == "mmd":
        for _, row in group.iterrows():
            plan_no = row["plan_no"]
            election_res = mmd_election(
                mmd_csv, plan_no, row["mmd_dist"], row["num_dist"]
            )
            republican_wins += election_res["rep_wins"]
            democratic_wins += election_res["dem_wins"]

    return pd.Series({"republican": republican_wins, "democratic": democratic_wins})


def group_by_plan_type(gdf: gpd.GeoDataFrame, plan_type: str, state: str):
    if plan_type == "smd":
        grouped_plan = gdf.groupby(gdf["districtId"].str.split("_").str[1])
        num_districts = {"MS": 4, "AL": 7, "PA": 18}.get(state, 0)
    elif plan_type == "mmd":
        grouped_plan = gdf.groupby(gdf["plan_no"].str.split("_").str[1])
        num_districts = {"MS": 1, "AL": 2, "PA": 4}.get(state, 0)
    return grouped_plan, num_districts


def calculate_population_ratios(row: gpd.GeoDataFrame):
    total_pop = int(row["POP"])
    return {
        "black": int(row["POP_BLACK"]) / total_pop,
        "hispanic": int(row["POP_HISP"]) / total_pop,
        "asian": int(row["POP_ASIAN"]) / total_pop,
        "nonWhite": (
            int(row["POP_BLACK"]) + int(row["POP_HISP"]) + int(row["POP_ASIAN"])
        )
        / total_pop,
        "republican": int(row["PRE20_R"]) / total_pop,
        "democratic": int(row["PRE20_D"]) / total_pop,
    }


def calculate_enacted_ratios(enacted_gdf: gpd.GeoDataFrame):
    ratios = {
        "black": [],
        "hispanic": [],
        "asian": [],
        "nonWhite": [],
        "republican": [],
        "democratic": [],
    }
    for _, row in enacted_gdf.iterrows():
        total_pop = int(row["total_pop"])
        ratios["black"].append(row["total_blk"] / total_pop)
        ratios["hispanic"].append(row["total_hsp"] / total_pop)
        ratios["asian"].append(row["total_asn"] / total_pop)
        ratios["nonWhite"].append(
            (row["total_asn"] + row["total_blk"] + row["total_hsp"]) / total_pop
        )
        ratios["republican"].append(row["vote_rep"] / total_pop)
        ratios["democratic"].append(row["vote_dem"] / total_pop)
    return {key: sorted(lst) for key, lst in ratios.items()}


def add_enacted_ratios(box_whisker: dict, enacted_ratios: dict, num_districts: int):
    for key in box_whisker.keys():
        for i in range(num_districts):
            box_whisker[key][i]["enacted"] = round(enacted_ratios[key][i], 3)


def collect_population_ratios(grouped_plan: gpd.GeoDataFrame, num_districts: int):
    population_percentages = {
        "black": [[] for _ in range(num_districts)],
        "hispanic": [[] for _ in range(num_districts)],
        "asian": [[] for _ in range(num_districts)],
        "nonWhite": [[] for _ in range(num_districts)],
        "republican": [[] for _ in range(num_districts)],
        "democratic": [[] for _ in range(num_districts)],
    }
    for _, single_plan in grouped_plan:
        district_ratios = {key: [] for key in population_percentages.keys()}
        for _, row in single_plan.iterrows():
            ratios = calculate_population_ratios(row)
            for key, value in ratios.items():
                district_ratios[key].append(value)
        for key, lst in district_ratios.items():
            lst = sorted(lst)
            for i in range(len(lst)):
                population_percentages[key][i].append(lst[i])
    return population_percentages


def compute_box_stats(data: np.array):
    return {
        "min": np.round(np.min(data), 3),
        "lowerQuartile": np.round(np.percentile(data, 25), 3),
        "median": np.round(np.median(data), 3),
        "upperQuartile": np.round(np.percentile(data, 75), 3),
        "max": np.round(np.max(data), 3),
        "average": np.round(np.mean(data), 3),
    }


def create_box_whisker(
    ensemble_path: str, enacted_path: str, plan_type: str, state: str
):  # Main function
    gdf = gpd.read_file(ensemble_path)
    grouped_plan, num_districts = group_by_plan_type(gdf, plan_type, state)
    population_percentages = collect_population_ratios(grouped_plan, num_districts)
    box_whisker = {
        key: [compute_box_stats(np.array(district_data)) for district_data in data]
        for key, data in population_percentages.items()
    }
    if plan_type == "smd":
        enacted_gdf = gpd.read_file(enacted_path)
        enacted_ratios = calculate_enacted_ratios(enacted_gdf)
        add_enacted_ratios(box_whisker, enacted_ratios, num_districts)
    for key in box_whisker.keys():
        for i in range(num_districts):
            box_whisker[key][i]["name"] = i + 1
    return box_whisker


def write_ensemble_summary(
    ensemble_path: str, plan_type: str, state: str
):  # Main function
    summary = {
        "num_dist_plan": 0,
        "avg_min_max_diff": 0.0,
        "layout": [],
    }
    gdf = gpd.read_file(ensemble_path)
    plan_populations = {}
    grouped_plan, district_num_by_state = group_by_plan_type(gdf, plan_type, state)

    for _, single_plan in grouped_plan:
        for _, row in single_plan.iterrows():
            graph_id = (
                int(row["districtId"].split("_")[1])
                if plan_type == "smd"
                else int(row["plan_no"].split("_")[1])
            )
            district_population = int(row["POP"])
            if graph_id not in plan_populations:
                plan_populations[graph_id] = []
            plan_populations[graph_id].append(district_population)
    min_max_diff_list = compute_population_diff(plan_populations)
    avg_min_max_diff = round(np.mean(np.array(min_max_diff_list)) * 100, 3)
    summary["layout"] = get_district_layout(plan_type, state)
    summary["avg_min_max_diff"] = avg_min_max_diff
    total_plan_num = len(gdf) // district_num_by_state
    summary["num_dist_plan"] = int(total_plan_num)

    return summary


def get_district_layout(plan_type: str, state: str):
    if plan_type == "mmd":
        layouts = {"MS": [4], "AL": [3, 4], "PA": [5, 5, 5, 3]}
        return layouts.get(state, [])


def compute_population_diff(plan_populations: dict):
    min_max_diff_list = []
    for _, populations in plan_populations.items():
        min_pop = min(populations)
        max_pop = max(populations)
        average = np.mean(np.array(populations))
        avg_max, avg_min = abs(max_pop - average), abs(min_pop - average)
        min_max_diff = max(avg_max, avg_min)
        total_pop = sum(populations)
        scaled_diff = min_max_diff / total_pop
        min_max_diff_list.append(scaled_diff)
    return min_max_diff_list


def process_enacted_election_data(enacted: gpd.GeoDataFrame):
    enacted_data = {
        "republican": 0,
        "democratic": 0,
        "numOpportunityRepresentatives": 0,
    }
    for _, row in enacted.iterrows():
        if row["win_pty"] == "REPUBLICANS":
            enacted_data["republican"] += 1
        elif row["win_pty"] == "DEMOCRATS":
            enacted_data["democratic"] += 1
        if (
            int(row["total_asn"]) + int(row["total_blk"]) + int(row["total_hsp"])
        ) / int(row["total_pop"]) > 0.5:
            enacted_data["numOpportunityRepresentatives"] += 1
    return enacted_data


def process_mmd_election_data(mmd: gpd.GeoDataFrame, mmd_csv_path: str):
    avg_mmd_data = {
        "republican": 0.0,
        "democratic": 0.0,
        "numOpportunityRepresentatives": 0.0,
    }
    for _, row in mmd.iterrows():
        num_dist = row["num_dist"]
        plan_no = row["plan_no"]
        mmd_dist = row["mmd_dist"]
        election_res = mmd_election(mmd_csv_path, plan_no, mmd_dist, num_dist)

        avg_mmd_data["republican"] += election_res["rep_wins"]
        avg_mmd_data["democratic"] += election_res["dem_wins"]
        op_threshold = set_op_threshold(num_dist)
        non_white_ratio = (
            int(row["POP_BLACK"]) + int(row["POP_HISP"]) + int(row["POP_ASIAN"])
        ) / int(row["POP"])
        if non_white_ratio > op_threshold:
            avg_mmd_data["numOpportunityRepresentatives"] += int(
                non_white_ratio // op_threshold
            )

    return avg_mmd_data


def enacted_vs_mmd(
    enacted_path: str, mmd_ensemble_path: str, mmd_csv_path: str
):  # Main function
    enacted_vs_avg_mmd = {
        "enacted": {
            "republican": 0,
            "democratic": 0,
            "numOpportunityRepresentatives": 0,
            "seatsVotes": [],
        },
        "averageMmd": {
            "republican": 0,
            "democratic": 0,
            "numOpportunityRepresentatives": 0,
            "seatsVotes": [],
        },
    }
    filtered_plan = list(avg_mmd_op_cand.intersection(avg_mmd_party_cand))
    selected_plan = random.choice(filtered_plan)
    print("filtered plan ...\n", filtered_plan)
    print("\nselected plan ==", selected_plan)
    enacted = gpd.read_file(enacted_path)
    mmd = gpd.read_file(mmd_ensemble_path)
    mmd = mmd[mmd["plan_no"] == selected_plan]
    enacted_data = process_enacted_election_data(enacted)
    enacted_vs_avg_mmd["enacted"] = enacted_data
    enacted_vs_avg_mmd["enacted"]["seatsVotes"] = seat_vote(enacted_path, "smd")["smd"]
    enacted_vs_avg_mmd["averageMmd"]["seatsVotes"] = seat_vote(
        mmd_ensemble_path, "mmd"
    )["mmd"]
    avg_mmd_data = process_mmd_election_data(mmd, mmd_csv_path)
    enacted_vs_avg_mmd["averageMmd"].update(avg_mmd_data)
    avg_mmd_party_cand.clear()
    avg_mmd_op_cand.clear()
    return enacted_vs_avg_mmd


if __name__ == "__main__":
    states = ["MS", "AL", "PA"]
    plans = ["smd", "mmd"]
    for state in states:
        for plan in plans:
            box = create_box_whisker(
                f"./dataset/5000_ensemble/{state}_{plan}_ensemble.geojson",
                f"./dataset/District/Election/{state}_dist_election_comb.geojson",
                plan,
                state,
            )
            seat_vote_curve = seat_vote(
                f"./dataset/5000_ensemble/{state}_{plan}_ensemble.geojson",
                plan,
            )
            if plan == "mmd":
                party_splits = create_party_split(
                    f"./dataset/5000_ensemble/{state}_{plan}_ensemble.geojson",
                    plan,
                    state,
                    f"./dataset/5000_ensemble/{state}_mmd_elec.csv",
                )
            elif plan == "smd":
                party_splits = create_party_split(
                    f"./dataset/5000_ensemble/{state}_{plan}_ensemble.geojson",
                    plan,
                    state,
                )

            op_bars = create_op_bar(
                f"./dataset/5000_ensemble/{state}_{plan}_ensemble.geojson", plan
            )

            summaries = write_ensemble_summary(
                f"./dataset/5000_ensemble/{state}_{plan}_ensemble.geojson", plan, state
            )
            if plan == "mmd":
                enacted_vs_avg = enacted_vs_mmd(
                    f"./dataset/District/Election/{state}_dist_election_comb.geojson",
                    f"./dataset/5000_ensemble/{state}_mmd_ensemble.geojson",
                    f"./dataset/5000_ensemble/{state}_mmd_elec.csv",
                )
                data_format["mmd"]["enactedAverage"]["enacted"] = enacted_vs_avg[
                    "enacted"
                ]
                data_format["mmd"]["enactedAverage"]["averageMmd"] = enacted_vs_avg[
                    "averageMmd"
                ]

            data_format["state"] = state
            if state == "PA":
                data_format["mmd"]["summary"]["layout"] = [5, 5, 5, 3]
            elif state == "MS":
                data_format["mmd"]["summary"]["layout"] = [4]
            elif state == "AL":
                data_format["mmd"]["summary"]["layout"] = [3, 4]

            for category in ["racial", "party"]:
                for k in data_format[plan][category][f"{category}BoxWhisker"].keys():
                    if k in box.keys():
                        data_format[plan][category][f"{category}BoxWhisker"][k] = box[k]

            data_format[plan]["summary"]["seatsVotes"] = seat_vote_curve[plan]
            data_format[plan]["summary"]["bias"] = seat_vote_curve["bias"]
            data_format[plan]["summary"]["symmetry"] = seat_vote_curve["symmetry"]
            data_format[plan]["summary"]["responsiveness"] = seat_vote_curve[
                "responsiveness"
            ]

            data_format[plan]["racial"]["opportunityDistricts"] = op_bars[
                "op_district_bar"
            ]

            data_format[plan]["racial"]["opportunityRepresentatives"] = op_bars[
                "op_representatives_bar"
            ]

            data_format[plan]["party"]["partySplits"] = party_splits["party_splits_bar"]
            data_format[plan]["party"]["averageSeatShare"] = seat_vote_curve[
                "avg_seat_share"
            ]
            data_format[plan]["summary"]["averagePartySplit"]["republican"] = (
                party_splits["avg_rep"]
            )

            data_format[plan]["summary"]["averagePartySplit"]["democratic"] = (
                party_splits["avg_dem"]
            )
            data_format[plan]["summary"]["averageNonWhiteRepresentatives"] = op_bars[
                "avg_op_repre"
            ]

            data_format[plan]["summary"]["numDistrictPlan"] = summaries["num_dist_plan"]
            data_format[plan]["summary"]["averageMinMaxDifference"] = summaries[
                "avg_min_max_diff"
            ]
        with open(f"./dataset/5000_ensemble/{state}_ensemble.json", "w") as f:
            json.dump(data_format, f, indent=2)
            f.close()
        print(f"{state} post_process done..")
