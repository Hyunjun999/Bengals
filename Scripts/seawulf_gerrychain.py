from shapely.ops import unary_union
import pandas as pd
import sys
import geopandas as gpd
import numpy as np

from gerrychain import (Partition, Graph, MarkovChain,
                        updaters, constraints, accept, tree)
from gerrychain.proposals import recom
from functools import partial
from tqdm import tqdm

def main():
    
    pa_file = ['./dataset/Precinct/PA_neigh_dist.shp', './dataset/Gerrychain/PA_ensemble.shp', 18, "PA"]
    ms_file = ['./dataset/Precinct/MS_neigh_dist.shp', './dataset/Gerrychain/MS_ensemble.shp', 4, "MS"]
    al_file = ['./dataset/Precinct/AL_neigh_dist.shp', './dataset/Gerrychain/AL_ensemble.shp', 7, "AL"]
    
    precinct_file = [ms_file, al_file, pa_file]
    
    for item in precinct_file:
        gdf = gpd.read_file(item[0])
        graph = Graph.from_geodataframe(gdf, ignore_errors=True)
        
        # ========= Updater setting ============
        seawulf_updaters = {}
        for column in gdf.columns:
            if column != 'geometry': 
                seawulf_updaters[column] = updaters.Tally(column)
        # ======================================  
         
        # ========= Initial Partition Setting ==========     
        ideal_population = gdf["POP"].sum() / item[2]
        dist_list = list(gdf['DISTRICT'].unique())
        
        random_assignment = tree.recursive_tree_part(graph, dist_list, ideal_population, "POP", 0.02)
        initial_partition = Partition(
            graph=graph, 
            assignment=random_assignment,
            updaters=seawulf_updaters
        )
        # ======================================
        
        # ========= Proposal & constraint Setting ==========
        proposal = partial(recom, 
                           pop_col="POP",
                           pop_target=ideal_population,
                           epsilon=0.02,
                           node_repeats=2
                           )
        
        pop_constraint = [constraints.within_percent_of_ideal_population(initial_partition, 0.2, pop_key="POP")]
        # ======================================
        
        df_template = []
        df_template.append("districtId")
        for column in gdf.columns:
            if column not in ["index", "REL_ID", "NEIGH_LIST", "DISTRICT", 'geometry']:
                df_template.append(column)
        
        ensemble_template = []
        ensemble_template.append("districtId")
        for column in gdf.columns:
            if column not in ["index", "REL_ID", "NEIGH_LIST", "DISTRICT"]:
                ensemble_template.append(column)
       
            
                
        ensemble = pd.DataFrame(columns=ensemble_template)
        
        # ========= Running the Markov Chain ==========
        for i in range(1):
            chain = MarkovChain(
                proposal=proposal,
                constraints=pop_constraint,
                accept=accept.always_accept,
                initial_state=initial_partition,
                total_steps=1000
                )
            
            for partition in chain.with_progress_bar():
                pass  # runs iterations
            
            id = "{}_{}".format(item[-1], str(i))
            
            # =========== Making the Dataframe for the result ===========
            precincts = []

            for node in partition.graph.nodes:
                temp = []
                # precinctId = partition.graph.nodes[node]['index']
                districtId = "{}_{}".format(id, str(partition.assignment[node]))
                temp.append(districtId)
                
                for column in df_template:
                    if column != "districtId":
                        temp.append(partition.graph.nodes[node][column])
                temp.append(partition.graph.nodes[node]['geometry'].buffer(0))
                
                precincts.append(temp)
            
            precincts = pd.DataFrame(precincts, columns=ensemble_template)
            
            districts_info = precincts[df_template].groupby('districtId').sum().reset_index()  # sum up numeric data of precincts by districtId
            
            districts_geo = precincts[['districtId', 'geometry']].groupby('districtId')['geometry'].apply(
                unary_union).reset_index() # Make union of all precincts to make a single district
            
            districts = districts_info.merge(districts_geo, on='districtId')
            ensemble = pd.concat([ensemble, districts], ignore_index=True)
        
        ensemble_gdf = gpd.GeoDataFrame(ensemble, geometry="geometry", crs="EPSG:4269")
        ensemble_gdf.to_file(item[1], driver='ESRI Shapefile')
        print("{} Ensemble Done...".format(item[-1]))

if __name__ == "__main__":
    main()
            
        
        
        