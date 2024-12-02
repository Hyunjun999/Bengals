package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.Feature;
import com.bengals.redistricting_project.Plans.Collections.Plan;
import com.bengals.redistricting_project.Plans.Collections.Property;
import com.bengals.redistricting_project.Plans.Collections.SampleMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SampleMapRepository sampleMapRepository;

    public PlanService(PlanRepository planRepository, SampleMapRepository sampleMapRepository) {
        this.planRepository = planRepository;
        this.sampleMapRepository = sampleMapRepository;
    }

    public Plan getPlan(String state, String reason, String districtType) {
        Plan plan = planRepository.findBy(state, reason, districtType).get(0);
        if (districtType.equalsIgnoreCase("mmd")) {
            List<Feature> features = plan.getFeatures();
            List<Feature> featuresDTO = new ArrayList<>();
            String partyWithVotes = "";
            for (Feature feature : features) {
                Property property = feature.getProperties();
                String[] winningParty = property.getWinningParty().split(",");
                String[] winningVotes = property.getWinningPartywithVotes().split(",");
                for (int i = 0; i < winningParty.length; i++) {
                    String sub = winningParty[i] + "(" + winningVotes[i] + "), ";
                    partyWithVotes = partyWithVotes + sub;
                }
                partyWithVotes = partyWithVotes.substring(0, partyWithVotes.length() - 2);
                property.setWinningParty(partyWithVotes);
                featuresDTO.add(feature);
            }
            plan.setFeatures(featuresDTO);
        }
        return plan;
    }

    public SampleMap getSampleMMDMap(String state) {
        return sampleMapRepository.findBy(state, "mmd");
    }

    public SampleMap getEnactedMap(String state) {
        return sampleMapRepository.findBy(state, "smd");
    }
}
