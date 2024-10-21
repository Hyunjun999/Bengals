import geopandas as gpd
import json
import pandas as pd
import pdb
pd.set_option('display.max_columns', None)


def main():
    pa_file = ['./dataset/PA_2020_vtd20/tl_2020_42_vtd20.shp', './dataset/state_vtd_2020/pa_vtd_2020.csv',
               "EPSG:4269", './dataset/Precinct/PA_preprocessed.json', "PA"]
    ms_file = ['./dataset/MS_2020_vtd20/tl_2020_28_vtd20.shp', './dataset/state_vtd_2020/ms_vtd_2020.csv',
               "EPSG:4269", './dataset/Precinct/MS_preprocessed.json', "MS"]
    al_file = ['./dataset/AL_2020_vtd20/tl_2020_01_vtd20.shp', './dataset/state_vtd_2020/al_vtd_2020.csv',
               "EPSG:4269", './dataset/Precinct/AL_preprocessed.json', "AL"]
    
    precinct_file = [pa_file, ms_file, al_file]
    
    for item in precinct_file:
        df_minority_prec = pd.read_csv(item[1])
        df_geo = gpd.read_file(item[0])
        
        # check if there are any difference in precinct id for two files
        temp_prec = []
        filtered = []
        
        for index, row in df_geo.iterrows():
            temp_prec.append(str(row["GEOID20"]))
        for index, row in df_minority_prec.iterrows():
            if (str(row["GEOID20"]) not in temp_prec):
                filtered.append(row["GEOID20"])
                
        # for outlier in filtered:
        #     print(outlier)
        temp_prec.clear()
        
        for index, row in df_minority_prec.iterrows():
            temp_prec.append(str(row["GEOID20"]))
        # for index, row in df_minority_prec2.iterrows():
        for index, row in df_geo.iterrows():
            if (str(row["GEOID20"]) not in temp_prec):
                filtered.append(row["GEOID20"])
        
        if (len(filtered) == 0):
            poly_dict = {}
            for index, row in df_geo.iterrows():
                if row['GEOID20'] not in poly_dict.keys():
                    poly_dict[row['GEOID20']] = {}
                    poly_dict[row['GEOID20']]['NAME20'] = row['NAME20']
                    poly_dict[row['GEOID20']]['INTPTLAT20'] = row['INTPTLAT20']
                    poly_dict[row['GEOID20']]['INTPTLON20'] = row['INTPTLON20']
                    poly_dict[row['GEOID20']]['geometry'] = row['geometry']
                else:
                    raise ValueError('outlier detected... please see the data again.')
            
            for index, row in df_minority_prec.iterrows():
                if row['GEOID20'] in poly_dict.keys():
                    poly_dict[row['GEOID20']]['pop'] = row['pop']
                    poly_dict[row['GEOID20']]['pop_hisp'] = row['pop_hisp']
                    poly_dict[row['GEOID20']]['pop_white'] = row['pop_white']
                    poly_dict[row['GEOID20']]['pop_black'] = row['pop_black']
                    poly_dict[row['GEOID20']]['pop_aian'] = row['pop_aian']
                    poly_dict[row['GEOID20']]['pop_asian'] = row['pop_asian']
                    poly_dict[row['GEOID20']]['pop_nhpi'] = row['pop_nhpi']
                    poly_dict[row['GEOID20']]['pop_other'] = row['pop_other']
                    poly_dict[row['GEOID20']]['pop_two'] = row['pop_two']
                    poly_dict[row['GEOID20']]['vap'] = row['vap']
                    poly_dict[row['GEOID20']]['vap_hisp'] = row['vap_hisp']
                    poly_dict[row['GEOID20']]['vap_white'] = row['vap_white']
                    poly_dict[row['GEOID20']]['vap_black'] = row['vap_black']
                    poly_dict[row['GEOID20']]['vap_aian'] = row['vap_aian']
                    poly_dict[row['GEOID20']]['vap_asian'] = row['vap_asian']
                    poly_dict[row['GEOID20']]['vap_nhpi'] = row['vap_nhpi']
                    poly_dict[row['GEOID20']]['vap_other'] = row['vap_other']
                    poly_dict[row['GEOID20']]['vap_two'] = row['vap_two']
                    match item[4]:
                        case "PA": 
                            poly_dict[row['GEOID20']]['pre_20_rep_tru'] = row['pre_20_rep_tru']
                            poly_dict[row['GEOID20']]['pre_20_dem_bid'] = row['pre_20_dem_bid']
                            poly_dict[row['GEOID20']]['atg_20_rep_hei'] = row['atg_20_rep_hei']
                            poly_dict[row['GEOID20']]['atg_20_dem_sha'] = row['atg_20_dem_sha']
                        case "MS":
                            poly_dict[row['GEOID20']]['pre_20_rep_tru'] = row['pre_20_rep_tru']
                            poly_dict[row['GEOID20']]['pre_20_dem_bid'] = row['pre_20_dem_bid']
                            poly_dict[row['GEOID20']]['uss_20_rep_hyd'] = row['uss_20_rep_hyd']
                            poly_dict[row['GEOID20']]['uss_20_dem_esp'] = row['uss_20_dem_esp']
                        case "AL":
                            poly_dict[row['GEOID20']]['pre_20_rep_tru'] = row['pre_20_rep_tru']
                            poly_dict[row['GEOID20']]['pre_20_dem_bid'] = row['pre_20_dem_bid']
                            poly_dict[row['GEOID20']]['uss_20_rep_tub'] = row['uss_20_rep_tub']
                            poly_dict[row['GEOID20']]['uss_20_dem_jon'] = row['uss_20_dem_jon']
                else:
                    raise ValueError('outlier detected... please see the data again.')
                
            precinct_df_pop_geo = []
            for key, value in poly_dict.items():
                temp = {}
                temp['PRECINCTID'] = key
                temp['pop'] = value['pop']
                temp['pop_hisp'] = value['pop_hisp']
                temp['pop_white'] = value['pop_white']
                temp['pop_black'] = value['pop_black']
                temp['pop_aian'] = value['pop_aian']
                temp['pop_asian'] = value['pop_asian']
                temp['pop_nhpi'] = value['pop_nhpi']
                temp['pop_other'] = value['pop_other']
                temp['pop_two'] = value['pop_two']
                temp['vap'] = value['vap']
                temp['vap_hisp'] = value['vap_hisp']
                temp['vap_white'] = value['vap_white']
                temp['vap_black'] = value['vap_black']
                temp['vap_aian'] = value['vap_aian']
                temp['vap_asian'] = value['vap_asian']
                temp['vap_nhpi'] = value['vap_nhpi']
                temp['vap_other'] = value['vap_other']
                temp['vap_two'] = value['vap_two']
                match item[4]:
                    case "PA":
                        temp['pre_20_rep_tru'] = value['pre_20_rep_tru']
                        temp['pre_20_dem_bid'] = value['pre_20_dem_bid']
                        temp['atg_20_rep_hei'] = value['atg_20_rep_hei']
                        temp['atg_20_dem_sha'] = value['atg_20_dem_sha']
                    case "MS":
                        temp['pre_20_rep_tru'] = value['pre_20_rep_tru']
                        temp['pre_20_dem_bid'] = value['pre_20_dem_bid']
                        temp['uss_20_rep_hyd'] = value['uss_20_rep_hyd']
                        temp['uss_20_dem_esp'] = value['uss_20_dem_esp']
                    case "AL":
                        temp['pre_20_rep_tru'] = value['pre_20_rep_tru']
                        temp['pre_20_dem_bid'] = value['pre_20_dem_bid']
                        temp['uss_20_rep_tub'] = value['uss_20_rep_tub']
                        temp['uss_20_dem_jon'] = value['uss_20_dem_jon']
                
                temp['INTPTLAT20'] = value['INTPTLAT20']
                temp['INTPTLON20'] = value['INTPTLON20']
                temp['geometry'] = value['geometry']
                
                precinct_df_pop_geo.append(temp)

            precinct_df = pd.DataFrame(precinct_df_pop_geo)
            ms_gdf = gpd.GeoDataFrame(precinct_df, geometry="geometry", crs=item[2])

            ms_gdf.to_file(item[3])
            print("Preprocess done for {}".format(item[4]))
            
        else:
            raise ValueError('non-overlapping precinct detected... please see the data again.')

    print("All preprocess done!")

if __name__ == "__main__":
    main()
        
    