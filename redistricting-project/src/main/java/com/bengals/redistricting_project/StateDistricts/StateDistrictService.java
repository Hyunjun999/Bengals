package com.bengals.redistricting_project.StateDistricts;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateDistrictService {
    private final StateDistrictRepository stateDistrictRepository;

    public StateDistrictService(StateDistrictRepository stateDistrictRepository) {
        this.stateDistrictRepository = stateDistrictRepository;
    }

    public List<List<StateDistrictReqDTO>> findAllMMDForOneState(String state, String disType) {
        List<StateDistrictReqDTO> each = stateDistrictRepository.findByDistrictIdStartingWithAndType(state, disType);
        List<StateDistrictReqDTO> subResult = new ArrayList<>();
        List<List<StateDistrictReqDTO>> result = new ArrayList<>();
        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            while (count < each.size()) {
                for (int i = 0; i < 2; i++) {
                    subResult.add(each.get(count + i));
                }
                result.add(new ArrayList<>(subResult));
                subResult.clear();
                count += 2;
            }
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            while (count < each.size()) {
                for (int i = 0; i < 1; i++) {
                    subResult.add(each.get(count + i));
                }
                result.add(new ArrayList<>(subResult));
                subResult.clear();
                count += 1;
            }
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            while (count < each.size()) {
                for (int i = 0; i < 4; i++) {
                    subResult.add(each.get(count + i));
                }
                result.add(new ArrayList<>(subResult));
                subResult.clear();
                count += 4;
            }
        }
        return result;
    }

    public List<List<StateDistrictReqDTO>> findAllSMDForOneState(String state) {
        List<StateDistrictReqDTO> each = stateDistrictRepository.findByDistrictIdStartingWithAndEndingWithNumber(state);
        List<StateDistrictReqDTO> subResult = new ArrayList<>();
        List<List<StateDistrictReqDTO>> result = new ArrayList<>();
        if (state.equalsIgnoreCase("AL")) {
            int count = 0;
            while (count < each.size()) {
                for (int i = 0; i < 7; i++) {
                    subResult.add(each.get(count + i));
                }
                result.add(new ArrayList<>(subResult));
                subResult.clear();
                count += 7;
            }
        } else if (state.equalsIgnoreCase("MS")) {
            int count = 0;
            while (count < each.size()) {
                for (int i = 0; i < 4; i++) {
                    subResult.add(each.get(count + i));
                }
                result.add(new ArrayList<>(subResult));
                subResult.clear();
                count += 4;
            }
        } else if (state.equalsIgnoreCase("PA")) {
            int count = 0;
            while (count < each.size()) {
                for (int i = 0; i < 18; i++) {
                    subResult.add(each.get(count + i));
                }
                result.add(new ArrayList<>(subResult));
                subResult.clear();
                count += 18;
            }
        }
        return result;
    }
}