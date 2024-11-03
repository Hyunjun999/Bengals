package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrictDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StateDistrictController {
    private final StateDistrictService stateDistrictService;

    public StateDistrictController(StateDistrictService stateDistrictService) {
        this.stateDistrictService = stateDistrictService;
    }

    @GetMapping("/{state}/all/districts/{disType}")
    public StateDistrictDTO getStateDistricts(@PathVariable String state, @PathVariable String disType) {
        return stateDistrictService.findStateDistrict(state, disType);
    }
}
