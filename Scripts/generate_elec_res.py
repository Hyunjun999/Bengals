import json, os, pandas as pd, glob, geopandas as gpd

house_election = os.path.join(
    "./HOUSE_precinct_general.csv"
)  # Enter your house election path
csv = pd.read_csv(house_election)
pd.set_option("display.max_columns", None)
# print(csv.head())

states = ["ALABAMA", "PENNSYLVANIA", "MISSISSIPPI"]
for s in states:
    election_res = {}
    for _, row in csv.iterrows():
        if row["state"] == s:
            district = row["district"]
            candidate = row["candidate"]
            if candidate in ["OVERVOTES", "UNDERVOTES", "WRITEIN"]:
                continue
            party = row["party_simplified"]
            votes = row["votes"]
            if district not in election_res:
                election_res[district] = {
                    "DISTRICT": district,
                    "DEM": "",
                    "REP": "",
                    "DEM_VOTE": 0,
                    "REP_VOTE": 0,
                    "TOT_VOTE": 0,
                    "WINNER": None,
                    "WINNER_PARTY": "",
                }
            if candidate not in election_res[district]["DEM"] and party == "DEMOCRAT":
                election_res[district]["DEM"] = candidate
                election_res[district]["DEM_VOTE"] += votes
                election_res[district]["TOT_VOTE"] += votes
            if candidate in election_res[district]["DEM"]:
                election_res[district]["DEM_VOTE"] += votes
                election_res[district]["TOT_VOTE"] += votes
            if candidate not in election_res[district]["REP"] and party == "REPUBLICAN":
                election_res[district]["REP"] = candidate
                election_res[district]["REP_VOTE"] += votes
                election_res[district]["TOT_VOTE"] += votes
            if candidate in election_res[district]["REP"]:
                election_res[district]["REP_VOTE"] += votes
                election_res[district]["TOT_VOTE"] += votes

    for district in election_res:
        if election_res[district]["DEM_VOTE"] > election_res[district]["REP_VOTE"]:
            election_res[district]["WINNER"] = election_res[district]["DEM"]
            election_res[district]["WINNER_PARTY"] = "DEMOCRATS"
        else:
            election_res[district]["WINNER"] = election_res[district]["REP"]
            election_res[district]["WINNER_PARTY"] = "REPUBLICANS"
    # election_res.columns = election_res.columns.str.lower()
    j = json.dumps(election_res, indent=4)
    with open(f"./{s}_house_election_2020.json", "w") as f:
        f.write(j)
        f.close()
print("house election json have been created")

geo_files = sorted(glob.glob("../district_data/*/*.geojson"))
json_files = sorted(glob.glob(f"./*_house_election_2020.json"))
# print(os.path.splitext(json_files[0])[0].split("/")[-1].split("_"))
states = ["AL", "MS", "PA"]
print((geo_files, json_files, states))
for geo_path, json_path, folder in zip(geo_files, json_files, states):
    geo = gpd.read_file(geo_path)
    json_file = pd.read_json(json_path).T

    geo["DISTRICT"] = geo["CD"]
    gdf = geo.merge(json_file, on="DISTRICT", how="left", suffixes=("", ""))
    gdf = gpd.GeoDataFrame(gdf, geometry="geometry", crs=4269)
    # projected_gdf = gdf.to_crs(epsg=4269)
    # projected_gdf["cen_cord"] = projected_gdf.geometry.centroid.apply(
    #     lambda point: f"[{point.x}, {point.y}]"
    # )

    # # Reproject back to the original CRS (if needed)
    # gdf = projected_gdf.to_crs(gdf.crs)
    select_col = [
        "C_TOT20",
        "VAP_TOT20",
        "C_ASN20",
        "C_BLK20",
        "C_HSP20",
        "C_WHT20",
        "DEM_VOTE",
        "REP_VOTE",
        "TOT_VOTE",
        "WINNER",
        "WINNER_PARTY",
        "geometry",
    ]
    gdf = gdf[select_col]
    gdf["TOT_VOTE"] = pd.to_numeric(gdf["TOT_VOTE"], errors="coerce").astype("Int64")
    gdf["DEM_VOTE"] = pd.to_numeric(gdf["DEM_VOTE"], errors="coerce").astype("Int64")
    gdf["REP_VOTE"] = pd.to_numeric(gdf["REP_VOTE"], errors="coerce").astype("Int64")
    gdf = gdf.rename(
        columns={
            "C_TOT20": "total_pop",
            "VAP_TOT20": "vote_pop",
            "C_ASN20": "total_asn",
            "C_BLK20": "total_blk",
            "C_HSP20": "total_hsp",
            "C_WHT20": "total_wht",
            "DEM_VOTE": "vote_dem",
            "REP_VOTE": "vote_rep",
            "TOT_VOTE": "total_vote",
            "WINNER": "win_cand",
            "WINNER_PARTY": "win_pty",
        }
    )
    gdf["centroid"] = gdf.geometry.centroid.apply(lambda point: f"{point.x},{point.y}")
    gdf.to_file(
        f"./{folder}/{folder}_dist_election_comb.geojson",
        driver="GeoJSON",
    )
    print(f"./{folder}/{folder}_dist_election_comb.geojson has been created!!")
