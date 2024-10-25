package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrictDto;
import com.bengals.redistricting_project.StateDistricts.MMD.StateDistrictServiceMMD;
import com.bengals.redistricting_project.StateDistricts.SMD.StateDistrictServiceSMD;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StateDistrictController {
    private final StateDistrictServiceMMD stateDistrictServiceMMD;
    private final StateDistrictServiceSMD stateDistrictServiceSMD;

    public StateDistrictController(StateDistrictServiceMMD stateDistrictServiceMMD,
                                   StateDistrictServiceSMD stateDistrictServiceSMD) {
        this.stateDistrictServiceMMD = stateDistrictServiceMMD;
        this.stateDistrictServiceSMD = stateDistrictServiceSMD;
    }

    @GetMapping("/{state}/all/districts/smd")
    public List<StateDistrictDto> getStateDistrictsMMD(@PathVariable String state) {
        return stateDistrictServiceSMD.findStateDistrictsSMD(state);
    }

    @GetMapping("/{state}/all/districts/mmd")
    public List<StateDistrictDto> getStateDistrictsSMD(@PathVariable String state) {
        return stateDistrictServiceMMD.findStateDistrictsMMD(state);
    }


}
