package com.bengals.redistricting_project;

import com.bengals.redistricting_project.Collection.StateDistrict;
import org.springframework.stereotype.Service;

@Service
public class StateService {

    private final StateDistrictRepository stateDistrictRepository;

    public StateService(StateDistrictRepository stateDistrictRepository) {
        this.stateDistrictRepository = stateDistrictRepository;
    }

    public StateDistrict findStateDistricts(String state) {
        for (StateDistrict stateDistrict : stateDistrictRepository.findAll()) {
            if (stateDistrict.getName().equals(state)) {
                return stateDistrict;
            }
        }
        return null;
    }

//    public List<StateDistrict> findAllDistricts() {
//        return stateDistrictRepository.findAll();
//    }
}
