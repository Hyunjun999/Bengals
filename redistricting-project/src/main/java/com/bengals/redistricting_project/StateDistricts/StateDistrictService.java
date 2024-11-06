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

        List<StateFeatureReqDTO> features = new ArrayList<>();
        List<List<StateFeatureReqDTO>> combinedFeatures = new ArrayList<>();
        List<StateDistrictResDTO> result = new ArrayList<>();

        StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
        stateDistrictResDTO.setId(districtPlan.get(0).getId());
        stateDistrictResDTO.setType(districtPlan.get(0).getType());
        stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());

        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            while (count < districtPlan.size()) {
                for (int i = 0; i < 2; i++) {
                    features.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                combinedFeatures.add(new ArrayList<>(features));
                features.clear();
                count += 2;
            }
            stateDistrictResDTO.setFeatures(combinedFeatures);
            result.add(stateDistrictResDTO);
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            while (count < districtPlan.size()) {
                for (int i = 0; i < 1; i++) {
                    features.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                combinedFeatures.add(new ArrayList<>(features));
                features.clear();
                count += 1;
            }
            stateDistrictResDTO.setFeatures(combinedFeatures);
            result.add(stateDistrictResDTO);
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            while (count < districtPlan.size()) {
                for (int i = 0; i < 4; i++) {
                    features.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                combinedFeatures.add(new ArrayList<>(features));
                features.clear();
                count += 4;
            }
            stateDistrictResDTO.setFeatures(combinedFeatures);
            result.add(stateDistrictResDTO);
        }
        return result;
    }

    public List<StateDistrictResDTO> findAllSMDForOneState(String state) {

        List<StateDistrictReqDTO> districtPlan = stateDistrictRepository.findByDistrictIdStartingWithAndEndingWithNumber(state);

        List<StateFeatureReqDTO> features = new ArrayList<>();
        List<List<StateFeatureReqDTO>> combinedFeatures = new ArrayList<>();
        List<StateDistrictResDTO> result = new ArrayList<>();

        StateDistrictResDTO stateDistrictResDTO = new StateDistrictResDTO();
        stateDistrictResDTO.setId(districtPlan.get(0).getId());
        stateDistrictResDTO.setType(districtPlan.get(0).getType());
        stateDistrictResDTO.setCrs(districtPlan.get(0).getCrs());

        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            while (count < districtPlan.size()) {
                for (int i = 0; i < 7; i++) {
                    features.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                combinedFeatures.add(new ArrayList<>(features));
                features.clear();
                count += 7;
            }
            stateDistrictResDTO.setFeatures(combinedFeatures);
            result.add(stateDistrictResDTO);
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            while (count < districtPlan.size()) {
                for (int i = 0; i < 4; i++) {
                    features.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                combinedFeatures.add(new ArrayList<>(features));
                features.clear();
                count += 4;
            }
            stateDistrictResDTO.setFeatures(combinedFeatures);
            result.add(stateDistrictResDTO);
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            while (count < districtPlan.size()) {
                for (int i = 0; i < 18; i++) {
                    features.add(districtPlan.get(count + i).getFeatures().get(0));
                }
                combinedFeatures.add(new ArrayList<>(features));
                features.clear();
                count += 18;
            }
            stateDistrictResDTO.setFeatures(combinedFeatures);
            result.add(stateDistrictResDTO);
        }
        return result;
    }
}