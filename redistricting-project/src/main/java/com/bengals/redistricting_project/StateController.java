package com.bengals.redistricting_project;

import com.bengals.redistricting_project.Collection.StateDistrict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
       this.stateService = stateService;
    }

    @GetMapping("/{state}/all/districts")
    public StateDistrict getStateDistricts(@PathVariable String state) {
        return stateService.findStateDistricts(state);
    }
}
