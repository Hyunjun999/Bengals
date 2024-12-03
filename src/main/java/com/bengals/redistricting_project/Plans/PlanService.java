package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.Feature;
import com.bengals.redistricting_project.Plans.Collections.Plan;
import com.bengals.redistricting_project.Plans.Collections.Property;
import com.bengals.redistricting_project.Plans.Collections.SampleMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final SampleMapRepository sampleMapRepository;

    public PlanService(PlanRepository planRepository, SampleMapRepository sampleMapRepository) {
        this.planRepository = planRepository;
        this.sampleMapRepository = sampleMapRepository;
    }

    public Plan getPlan(String state, String reason, String districtType) {
        String shortenedReason = getShortenedReason(reason);
        Plan plan = planRepository.findBy(state, shortenedReason, districtType).get(0);
        if (districtType.equalsIgnoreCase("mmd")) {
            List<Feature> updatedFeatures = processFeatures(plan.getFeatures());
            plan.setFeatures(updatedFeatures);
        }
        return plan;
    }

    public SampleMap getSampleMMDMap(String state) {
        SampleMap sampleMap = sampleMapRepository.findBy(state, "mmd");
        List<Feature> features = sampleMap.getFeatures();
        sampleMap.setFeatures(processFeatures(features));
        return sampleMap;
    }

    public SampleMap getEnactedMap(String state) {
        return sampleMapRepository.findBy(state, "smd");
    }

    private String getShortenedReason(String reason) {
        Map<String, String> reasonMap = Map.of(
                "republican", "rep",
                "democratic", "dem",
                "opportunity-max", "op_max",
                "white-max", "wht_prob_max",
                "non-white-max", "non_wht_prob_max"
        );
        return reasonMap.getOrDefault(reason, reason);
    }

    private List<Feature> processFeatures(List<Feature> features) {
        List<Feature> featuresDTO = new ArrayList<>();
        for (Feature feature : features) {
            Property property = feature.getProperties();
            String partyWithVotes = generatePartyWithVotes(property);
            property.setWinningParty(partyWithVotes);
            featuresDTO.add(feature);
        }
        return featuresDTO;
    }

    private String generatePartyWithVotes(Property property) {
        String[] winningParty = property.getWinningParty().split(",");
        String[] winningVotes = property.getWinningPartyVotes().split(",");
        StringBuilder partyWithVotes = new StringBuilder();
        for (int i = 0; i < winningParty.length; i++) {
            partyWithVotes.append(winningParty[i]).append("(").append(winningVotes[i]).append("), ");
        }
        return partyWithVotes.substring(0, partyWithVotes.length() - 2);
    }
}
