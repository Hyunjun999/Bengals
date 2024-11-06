package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.DistrictPlanStat.StateDistrictPlanStat;
import com.bengals.redistricting_project.DistrictPlanStat.StateDistrictPlanStatRepository;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateFeatureReqDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateDistrictService {
    private final StateDistrictRepository stateDistrictRepository;
    private final StateDistrictPlanStatRepository stateDistrictPlanStatRepository;

    public StateDistrictService(StateDistrictRepository stateDistrictRepository, StateDistrictPlanStatRepository stateDistrictPlanStatRepository) {
        this.stateDistrictRepository = stateDistrictRepository;
        this.stateDistrictPlanStatRepository = stateDistrictPlanStatRepository;
    }

    public List<StateDistrictResDTO> findAllMMDForOneState(String state, String disType) {
        List<StateDistrictReqDTO> districtPlan = stateDistrictRepository.findByDistrictIdStartingWithAndType(state, disType);
        List<StateDistrictPlanStat> districtPlanStats = stateDistrictPlanStatRepository.findByStateAndDisType(state.toUpperCase(), disType.toUpperCase());
        System.out.println(districtPlanStats);
        List<StateFeatureReqDTO> combinedFeatures = new ArrayList<>();
        List<StateDistrictResDTO> result = new ArrayList<>();

        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            int statCount = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                stateDistrictResDTO.setState(districtPlanStats.get(statCount).getState());
                stateDistrictResDTO.setDis_type(districtPlanStats.get(statCount).getDis_type());
                stateDistrictResDTO.setOp_districts(districtPlanStats.get(statCount).getOp_districts());
                stateDistrictResDTO.setSafe_districts(districtPlanStats.get(statCount).getSafe_districts());
                stateDistrictResDTO.setOp_threshold(districtPlanStats.get(statCount).getOp_threshold());
                stateDistrictResDTO.setRepublicans(districtPlanStats.get(statCount).getRepublicans());
                stateDistrictResDTO.setDemocrats(districtPlanStats.get(statCount).getDemocrats());
                for (int i = 0; i < 2; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 2;
                statCount++;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            int statCount = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                stateDistrictResDTO.setState(districtPlanStats.get(statCount).getState());
                stateDistrictResDTO.setDis_type(districtPlanStats.get(statCount).getDis_type());
                stateDistrictResDTO.setOp_districts(districtPlanStats.get(statCount).getOp_districts());
                stateDistrictResDTO.setSafe_districts(districtPlanStats.get(statCount).getSafe_districts());
                stateDistrictResDTO.setOp_threshold(districtPlanStats.get(statCount).getOp_threshold());
                stateDistrictResDTO.setRepublicans(districtPlanStats.get(statCount).getRepublicans());
                stateDistrictResDTO.setDemocrats(districtPlanStats.get(statCount).getDemocrats());
                for (int i = 0; i < 1; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 1;
                statCount++;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            int statCount = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                stateDistrictResDTO.setState(districtPlanStats.get(statCount).getState());
                stateDistrictResDTO.setDis_type(districtPlanStats.get(statCount).getDis_type());
                stateDistrictResDTO.setOp_districts(districtPlanStats.get(statCount).getOp_districts());
                stateDistrictResDTO.setSafe_districts(districtPlanStats.get(statCount).getSafe_districts());
                stateDistrictResDTO.setOp_threshold(districtPlanStats.get(statCount).getOp_threshold());
                stateDistrictResDTO.setRepublicans(districtPlanStats.get(statCount).getRepublicans());
                stateDistrictResDTO.setDemocrats(districtPlanStats.get(statCount).getDemocrats());
                for (int i = 0; i < 4; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 4;
                statCount++;
                result.add(stateDistrictResDTO);
            }
        }
        return result;
    }

    public List<StateDistrictResDTO> findAllSMDForOneState(String state) {
        List<StateDistrictReqDTO> districtPlan = stateDistrictRepository.findByDistrictIdStartingWithAndEndingWithNumber(state);
        List<StateDistrictPlanStat> districtPlanStats = stateDistrictPlanStatRepository.findByStateAndDisType(state.toUpperCase(), "SMD");

        List<StateFeatureReqDTO> combinedFeatures = new ArrayList<>();
        List<StateDistrictResDTO> result = new ArrayList<>();

        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            int statCount = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                stateDistrictResDTO.setState(districtPlanStats.get(statCount).getState());
                stateDistrictResDTO.setDis_type(districtPlanStats.get(statCount).getDis_type());
                stateDistrictResDTO.setOp_districts(districtPlanStats.get(statCount).getOp_districts());
                stateDistrictResDTO.setSafe_districts(districtPlanStats.get(statCount).getSafe_districts());
                stateDistrictResDTO.setOp_threshold(districtPlanStats.get(statCount).getOp_threshold());
                stateDistrictResDTO.setRepublicans(districtPlanStats.get(statCount).getRepublicans());
                stateDistrictResDTO.setDemocrats(districtPlanStats.get(statCount).getDemocrats());
                for (int i = 0; i < 7; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 7;
                statCount++;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            int statCount = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                stateDistrictResDTO.setState(districtPlanStats.get(statCount).getState());
                stateDistrictResDTO.setDis_type(districtPlanStats.get(statCount).getDis_type());
                stateDistrictResDTO.setOp_districts(districtPlanStats.get(statCount).getOp_districts());
                stateDistrictResDTO.setSafe_districts(districtPlanStats.get(statCount).getSafe_districts());
                stateDistrictResDTO.setOp_threshold(districtPlanStats.get(statCount).getOp_threshold());
                stateDistrictResDTO.setRepublicans(districtPlanStats.get(statCount).getRepublicans());
                stateDistrictResDTO.setDemocrats(districtPlanStats.get(statCount).getDemocrats());
                for (int i = 0; i < 4; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 4;
                statCount++;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            int statCount = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                stateDistrictResDTO.setState(districtPlanStats.get(statCount).getState());
                stateDistrictResDTO.setDis_type(districtPlanStats.get(statCount).getDis_type());
                stateDistrictResDTO.setOp_districts(districtPlanStats.get(statCount).getOp_districts());
                stateDistrictResDTO.setSafe_districts(districtPlanStats.get(statCount).getSafe_districts());
                stateDistrictResDTO.setOp_threshold(districtPlanStats.get(statCount).getOp_threshold());
                stateDistrictResDTO.setRepublicans(districtPlanStats.get(statCount).getRepublicans());
                stateDistrictResDTO.setDemocrats(districtPlanStats.get(statCount).getDemocrats());
                for (int i = 0; i < 18; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 18;
                statCount++;
                result.add(stateDistrictResDTO);
            }
        }
        return result;
    }
}