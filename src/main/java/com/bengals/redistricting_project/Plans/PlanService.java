package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.Plan;
import com.bengals.redistricting_project.Plans.Collections.SampleMap;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SampleMapRepository sampleMapRepository;

    public PlanService(PlanRepository planRepository, SampleMapRepository sampleMapRepository) {
        this.planRepository = planRepository;
        this.sampleMapRepository = sampleMapRepository;
    }

    public Plan getPlan(String state, String reason, String districtType) {
        return planRepository.findBy(state, reason, districtType).get(0);
    }

    public SampleMap getSampleMMDMap(String state) {
        return sampleMapRepository.findBy(state, "mmd");
    }

    public SampleMap getEnactedMap(String state) {
        return sampleMapRepository.findBy(state, "smd");
    }
}
