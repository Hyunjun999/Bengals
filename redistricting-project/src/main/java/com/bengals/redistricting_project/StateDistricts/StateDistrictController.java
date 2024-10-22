package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.ALStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StateDistrictController {
    private final StateDistrictService stateDistrictService;

    public StateDistrictController(StateDistrictService stateDistrictService) {
       this.stateDistrictService = stateDistrictService;
    }

    @GetMapping("/{state}/all/districts")
    public StateDistrict getStateDistricts(@PathVariable String state) {
        return stateDistrictService.
                findStateDistricts(state);
    }
}
