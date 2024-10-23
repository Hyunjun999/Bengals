package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrictDto;
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

//    @GetMapping("/{state}/all/districts")
//    public StateDistrictDto getStateDistricts(@PathVariable String state) {
//        return stateDistrictService.findStateDistricts(state);
//    }

    @GetMapping("/{state}/all/districts")
    public List<StateDistrictDto> getStateDistricts(@PathVariable String state) {
        return stateDistrictService.findStateDistricts(state);
    }
}
