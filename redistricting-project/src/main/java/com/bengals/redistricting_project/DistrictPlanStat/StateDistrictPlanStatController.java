package com.bengals.redistricting_project.DistrictPlanStat;

import com.bengals.redistricting_project.StateDistricts.StateDistrictReqDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StateDistrictPlanStatController {
    private final StateDistrictPlanStatService stateDistrictPlanStatService;

    public StateDistrictPlanStatController(StateDistrictPlanStatService stateDistrictPlanStatService) {
        this.stateDistrictPlanStatService = stateDistrictPlanStatService;
    }

    @GetMapping("/{state}/plan/{disType}")
    public List<StateDistrictPlanStat> getStateDistricts(@PathVariable String state, @PathVariable String disType) {
        if (disType.equalsIgnoreCase("MMD")) {
            return stateDistrictPlanStatService.findAllMMDPlanStat(state, disType);
        } else {
            return stateDistrictPlanStatService.findAllSMDPlanStat(state);
        }
    }
}
