package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import org.springframework.stereotype.Service;

@Service
public class StateDistrictService {

    private final StateDistrictRepository stateDistrictRepository;

    public StateDistrictService(StateDistrictRepository stateDistrictRepository) {
        this.stateDistrictRepository = stateDistrictRepository;
    }

    public StateDistrict findStateDistricts(String state) {
        for (StateDistrict stateDistrict : stateDistrictRepository.findAll()) {
            if (stateDistrict.getName().startsWith(state)) {
                return stateDistrict;
            }
        }
        return null;
    }

//    public List<StateDistrict> findAllDistricts() {
//        return stateDistrictRepository.findAll();
//    }
}
