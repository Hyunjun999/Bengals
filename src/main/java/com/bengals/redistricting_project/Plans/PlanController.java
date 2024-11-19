package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.Plan;
import com.bengals.redistricting_project.Plans.Collections.SampleMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/{state}/randomPlan/{reason}/{districtType}")
    public Plan getPlan(@PathVariable String state, @PathVariable String reason, @PathVariable String districtType) {
        return planService.getPlan(state, reason, districtType.toLowerCase());
    }

    @GetMapping("/{state}/sampleMMDMap")
    public SampleMap getSampleMMDMap(@PathVariable String state) {
        return planService.getSampleMMDMap(state);
    }

    @GetMapping("/{state}/enactedMap")
    public SampleMap getEnactedMap(@PathVariable String state) {
        return planService.getEnactedMap(state);
    }

}
