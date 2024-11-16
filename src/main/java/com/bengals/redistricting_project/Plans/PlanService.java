package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.Plan;
import com.bengals.redistricting_project.Plans.Repository.PlanRepository;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public Plan getHighestRepPlan(String state, String reason, String districtType) {
        return planRepository.findBy(state, reason, districtType).get(0);
    }
}
