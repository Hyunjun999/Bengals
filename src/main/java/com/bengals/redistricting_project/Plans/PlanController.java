package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Ensembles.DTO.EnsembleSummaryDTO;
import com.bengals.redistricting_project.Plans.Collections.Plan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/{state}/randomPlan/{reason}/{districtType}")
    public Plan getHighestRepPlan(@PathVariable String state, @PathVariable String reason, @PathVariable String districtType) {
        return planService.getHighestRepPlan(state, reason, districtType.toLowerCase());
    }
}
