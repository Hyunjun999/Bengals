package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateFeatureReqDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateDistrictService {
    private final StateDistrictRepository stateDistrictRepository;

    public StateDistrictService(StateDistrictRepository stateDistrictRepository) {
        this.stateDistrictRepository = stateDistrictRepository;
    }

    public List<StateDistrictResDTO> findAllMMDForOneState(String state, String disType) {
        List<StateDistrictReqDTO> districtPlan = stateDistrictRepository.findByDistrictIdStartingWithAndType(state, disType);

        List<StateFeatureReqDTO> combinedFeatures = new ArrayList<>();
        List<StateDistrictResDTO> result = new ArrayList<>();

        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                for (int i = 0; i < 2; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 2;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                for (int i = 0; i < 1; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 1;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                for (int i = 0; i < 4; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 4;
                result.add(stateDistrictResDTO);
            }
        }
        return result;
    }

    public List<StateDistrictResDTO> findAllSMDForOneState(String state) {
        List<StateDistrictReqDTO> districtPlan = stateDistrictRepository.findByDistrictIdStartingWithAndEndingWithNumber(state);

        List<StateFeatureReqDTO> combinedFeatures = new ArrayList<>();
        List<StateDistrictResDTO> result = new ArrayList<>();

        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                for (int i = 0; i < 7; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 7;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                for (int i = 0; i < 4; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 4;
                result.add(stateDistrictResDTO);
            }
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            while (count < districtPlan.size()) {
                StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
                stateDistrictResDTO.setId(districtPlan.get(0).getId());
                stateDistrictResDTO.setType(districtPlan.get(0).getType());
                stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());
                for (int i = 0; i < 18; i++) {
                    combinedFeatures.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                stateDistrictResDTO.setFeatures(new ArrayList<>(combinedFeatures));
                combinedFeatures.clear();
                count += 18;
                result.add(stateDistrictResDTO);
            }
        }
        return result;
    }
}