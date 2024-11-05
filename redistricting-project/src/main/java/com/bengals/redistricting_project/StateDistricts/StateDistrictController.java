package com.bengals.redistricting_project.StateDistricts;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StateDistrictController {
    private final StateDistrictService stateDistrictService;

    public StateDistrictController(StateDistrictService stateDistrictService) {
        this.stateDistrictService = stateDistrictService;
    }

    @GetMapping("/{state}/all/districts/{disType}")
    public List<List<StateDistrictReqDTO>> getStateDistricts(@PathVariable String state, @PathVariable String disType) {
        if (disType.equalsIgnoreCase("MMD")) {
            return stateDistrictService.findAllMMDForOneState(state, disType);
        }
        else {
            return stateDistrictService.findAllSMDForOneState(state);
        }
    }
}
