import geopandas as gpd
import json
import pandas as pd
import maup
from tqdm import tqdm

pd.set_option('display.max_columns', None)

def find_district(districts, precinct_geom):
    # Intersection area between each district and the precinct
    inter_areas = {}
        
    for idx, row in districts.iterrows():
        district_id = row["CD"]
        district_geom = row["geometry"]
        
        inter = district_geom.intersection(precinct_geom)
        inter_areas[district_id] = inter.area

    max_area = 0
    district = 0

    # Return the district with the maximum intersection area
    for id, area in inter_areas.items():
        if area > max_area:
            max_area = area
            district = id

    return int(district)

def main():
    
    pa_file = ['./dataset/Precinct/PA_preprocessed.json', "EPSG:4269", 
               './dataset/Precinct/PA_neigh_dist.shp', "./dataset/District/pa_cvap_2020_cd.geojson","PA"]
    ms_file = ['./dataset/Precinct/MS_preprocessed.json', "EPSG:4269", 
               './dataset/Precinct/MS_neigh_dist.shp', "./dataset/District/ms_cvap_2020_cd.geojson", "MS"]
    al_file = ['./dataset/Precinct/AL_preprocessed.json', "EPSG:4269", 
               './dataset/Precinct/AL_neigh_dist.shp', "./dataset/District/al_cvap_2020_cd.geojson", "AL"]
    
    precinct_file = [ms_file, al_file, pa_file]
    
    for item in precinct_file:
        print("Preprocessing neighbor for {}".format(item[-1]))
        # if(item[-1] == "MS"):
        #     from pyproj import Transformer

        #     # This is to convert the relative coordinate system from 3814 to 4269 (Longitute & Latitute system)
        #     with open('./dataset/Precinct/MS_preprocessed.json', 'r') as f:
        #         geojson_data = json.load(f)

        #     # Define the source and destination EPSG codes
        #     source_epsg = 'EPSG:3814'  
        #     dest_epsg = 'EPSG:4269'    

        #     # Initialize the transformer
        #     transformer = Transformer.from_crs(source_epsg, dest_epsg, always_xy=True)

        #     # Function to transform coordinates
        #     def transform_coords(coords):
        #         if isinstance(coords[0], list):  # In case of nested coordinates
        #             return [transform_coords(c) for c in coords]
        #         else:
        #             return transformer.transform(coords[0], coords[1])

        #     # Transform all the polygon coordinates in the GeoJSON
        #     for feature in geojson_data['features']:
        #         geometry_type = feature['geometry']['type']
        #         if geometry_type == 'Polygon':
        #             feature['geometry']['coordinates'] = [transform_coords(ring) for ring in feature['geometry']['coordinates']]
                

        #     # Save the transformed geojson
        #     with open('./dataset/Precinct/MS_preprocessed_4269.json', 'w') as f:
        #         json.dump(geojson_data, f, indent=4)
                
            # df = gpd.read_file(item[0])
            # df = gpd.GeoDataFrame(df, geometry="geometry")
            # df = df.set_crs("EPSG:4269", allow_override=True)
            # df.to_file('./dataset/Precinct/MS_preprocessed_4269.geojson')
            

        gdf = gpd.read_file(item[0]) 
    
        neighbors = {}

        print("Iteration length: {}".format(len(gdf)))
        for idx, row in tqdm(gdf.iterrows()):
            # neighbor_polygons = df[df.geometry.touches(row["geometry"].geometry)].index.tolist()
            neighbor_polygons = []
            for idx2, row2 in gdf.iterrows():
                if (row["geometry"].intersects(row2["geometry"])):
                    neighbor_polygons.append(idx2)
                    
                    # remove myself from the current polygon's index
                    if idx in neighbor_polygons:
                        neighbor_polygons.remove(idx)
            neighbor_polygons = str(neighbor_polygons) + ""
            # Store the neighbors & other existing informations
            neighbors[row["PRECINCTID"]] = {}
            neighbors[row["PRECINCTID"]]["REL_ID"] = idx
            neighbors[row["PRECINCTID"]]["NEIGH_LIST"] = neighbor_polygons
            neighbors[row["PRECINCTID"]]["POP"] = row["pop"]
            neighbors[row['PRECINCTID']]['POP_HISP'] = row['pop_hisp']
            neighbors[row['PRECINCTID']]['POP_WHITE'] = row['pop_white']
            neighbors[row['PRECINCTID']]['POP_BLACK'] = row['pop_black']
            neighbors[row['PRECINCTID']]['POP_AIAN'] = row['pop_aian']
            neighbors[row['PRECINCTID']]['POP_ASIAN'] = row['pop_asian']
            neighbors[row['PRECINCTID']]['POP_NHIP'] = row['pop_nhpi']
            neighbors[row['PRECINCTID']]['POP_OTHER'] = row['pop_other']
            neighbors[row['PRECINCTID']]['POP_TWO'] = row['pop_two']
            neighbors[row['PRECINCTID']]['VAP'] = row['vap']
            neighbors[row['PRECINCTID']]['VAP_HISP'] = row['vap_hisp']
            neighbors[row['PRECINCTID']]['VAP_WHITE'] = row['vap_white']
            neighbors[row['PRECINCTID']]['VAP_BLACK'] = row['vap_black']
            neighbors[row['PRECINCTID']]['VAP_AIAN'] = row['vap_aian']
            neighbors[row['PRECINCTID']]['VAP_ASIAN'] = row['vap_asian']
            neighbors[row['PRECINCTID']]['VAP_NHIP'] = row['vap_nhpi']
            neighbors[row['PRECINCTID']]['VAP_OTHER'] = row['vap_other']
            neighbors[row['PRECINCTID']]['VAP_TWO'] = row['vap_two']
            
            match item[-1]:
                case "PA": 
                    neighbors[row["PRECINCTID"]]["PRE20_R"] = row["pre_20_rep_tru"]
                    neighbors[row["PRECINCTID"]]["PRE20_D"] = row["pre_20_dem_bid"]
                    neighbors[row["PRECINCTID"]]["ATG20_R"] = row["atg_20_rep_hei"]
                    neighbors[row["PRECINCTID"]]["ATG20_D"] = row["atg_20_dem_sha"]
                case "MS":
                    neighbors[row["PRECINCTID"]]["PRE20_R"] = row["pre_20_rep_tru"]
                    neighbors[row["PRECINCTID"]]["PRE20_D"] = row["pre_20_dem_bid"]
                    neighbors[row["PRECINCTID"]]["SEN20_R"] = row["uss_20_rep_hyd"]
                    neighbors[row["PRECINCTID"]]["SEN20_D"] = row["uss_20_dem_esp"]
                case "AL":
                    neighbors[row["PRECINCTID"]]["PRE20_R"] = row["pre_20_rep_tru"]
                    neighbors[row["PRECINCTID"]]["PRE20_D"] = row["pre_20_dem_bid"]
                    neighbors[row["PRECINCTID"]]["SEN20_R"] = row["uss_20_rep_tub"]
                    neighbors[row["PRECINCTID"]]["SEN20_D"] = row["uss_20_dem_jon"]
    
            neighbors[row["PRECINCTID"]]["geometry"] = row["geometry"]
            
        df_dist = gpd.read_file(item[3])
        
        for polygon_prec_id, attribute in neighbors.items():
            neighbors[polygon_prec_id]['DISTRICT'] = find_district(df_dist, attribute["geometry"])
            
        df_com = pd.DataFrame.from_dict(neighbors, orient='index')
        
        df_copy = df_com.copy()
        col_to_move = df_copy.pop('DISTRICT')
        df_copy.insert(2, 'DISTRICT', col_to_move)
        
        # if(maup.doctor(df_copy)):
        crs_gdf = gpd.GeoDataFrame(df_copy, geometry="geometry")
        
        # Checks for any boundary related problems with maup.doctor
        if(maup.doctor(crs_gdf)):
            crs_gdf = crs_gdf.set_crs("EPSG:4269")
            crs_gdf.to_file(item[2], driver='ESRI Shapefile')
            print("Process done for {}...!".format(item[-1]))
            
    print("Total Process Done...!")

if __name__ == "__main__":
    main()

            
            