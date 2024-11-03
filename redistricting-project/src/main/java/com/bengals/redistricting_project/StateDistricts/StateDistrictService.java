package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateDistrictService {
    private final StateDistrictRepository stateDistrictRepository;

    public StateDistrictService(StateDistrictRepository stateDistrictRepository) {
        this.stateDistrictRepository = stateDistrictRepository;
    }

    public StateDistrictDTO findStateDistrict(String state, String disType) {
        StateDistrictType stateDistrictType = stateDistrictRepository.findByStateAndDisType(state.toUpperCase(), disType.toUpperCase());
        return StateDistrictDTO.builder()
                .id(stateDistrictType.getId())
                .type("FeatureCollection")
                .dis_type(stateDistrictType.getDis_type())
                .state(stateDistrictType.getState())
                .crs(stateDistrictType.getCrs())
                .op_districts(stateDistrictType.getOp_districts())
                .op_threshold(stateDistrictType.getOp_threshold())
                .safe_districts(stateDistrictType.getSafe_districts())
                .Republicans(stateDistrictType.getRepublicans())
                .Democrats(stateDistrictType.getDemocrats())
                .features(stateDistrictType.getFeatures())
                .build();
    }
}