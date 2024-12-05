package com.bengals.redistricting_project.States;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("/{state}/info")
    public State getStateInfo(@PathVariable String state) {
        return stateService.getStateInfo(state);
    }
}
