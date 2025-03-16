import geopandas as gpd, random, pandas as pd
from collections import defaultdict


def set_op_threshold(num_dist):
    thresholds = {3: 1 / 6, 4: 1 / 8, 5: 1 / 10}  # 1 / 2n
    return thresholds.get(num_dist, 0.5)


def random_seat_vote(gdf, plan_type):
    votingData = defaultdict(lambda: {"repVotes": 0, "demVotes": 0, "count": 0})
    votingByDistrict = []
    seatsVotesRep = []
    seatsVotesDem = []

    SWING_CONST = 0.01  # Percentage to increase each district by

    totalVotes, totalDemVotes, totalRepVotes = [0] * 3

    # Read all district data
    for i, r in gdf.iterrows():
        if plan_type == "smd":
            districtId = int(r["districtId"].split("_")[-1])
        elif plan_type == "mmd":
            districtId = r["num_dist"]
        rep = r["republicanVotes"]
        dem = r["democraticVotes"]

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
    d["bias"] = abs(
        int((d[plan_type][len(d[plan_type]) // 2]["republican"] - 50) * 10**3) / 10**3
    )
    d[plan_type].insert(
        0,
        {
            "republican": 0,
            "democratic": 0,
        },
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


def seat_vote(ensemble_path, plan_type):
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
            if "districtId" in gdf.keys():
                districtId = int(row["districtId"].split("_")[-1])
                rep = int(row["PRE20_R"])
                dem = int(row["PRE20_D"])
            else:
                districtId = i
                rep = int(row["vote_rep"])
                dem = int(row["vote_dem"])
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
    # print(plan_type, votingByDistrict)
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

    d = {plan_type: [], "avg_seat_share": {"republican": 0, "democratic": 0}}
    # Add seat-vote data for Republicans and Democrats
    for i in range(1, len(seatsVotesRep)):
        d[plan_type].append(
            {
                "republican": int(100 * seatsVotesRep[i]["seats"] * 10**6) / 10**6,
                "democratic": int(100 * seatsVotesDem[i]["seats"] * 10**6) / 10**6,
            }
        )
        if i == 49:
            d["avg_seat_share"]["republican"] = (
                int(100 * seatsVotesRep[i]["seats"] * 10**6) / 10**6
            )

            d["avg_seat_share"]["democratic"] = (
                int(100 * seatsVotesDem[i]["seats"] * 10**6) / 10**6
            )
    d["bias"] = abs(
        int((d[plan_type][len(d[plan_type]) // 2]["republican"] - 50) * 10**3) / 10**3
    )
    d[plan_type].insert(
        0,
        {
            "republican": 0,
            "democratic": 0,
        },
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


def mmd_election(csv_path, plan_no, mmd_dist, num_dist):
    df = pd.read_csv(csv_path)
    for _, r in df.iterrows():
        if (
            r["plan_no"] == plan_no
            and r["num_dist"] == num_dist
            and r["mmd_dist"] == mmd_dist
        ):
            candidate_votes = {
                c: int(r[c])
                for c in df.columns
                if (c.startswith("R_") or c.startswith("D_")) and pd.notna(r[c])
            }
            sorted_candidates = sorted(
                candidate_votes.items(), key=lambda x: x[1], reverse=True
            )[:num_dist]
            dem_wins = sum(
                1 for candidate, _ in sorted_candidates if candidate.startswith("D_")
            )
            rep_wins = sum(
                1 for candidate, _ in sorted_candidates if candidate.startswith("R_")
            )

            election_res = {
                "plan_no": r["plan_no"],
                "num_dist": num_dist,
                "elected_candidates": sorted_candidates,
                "dem_wins": dem_wins,
                "rep_wins": rep_wins,
            }
    return election_res
