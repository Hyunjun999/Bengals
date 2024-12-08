package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.Feature;
import com.bengals.redistricting_project.Plans.Collections.Plan;
import com.bengals.redistricting_project.Plans.Collections.Property;
import com.bengals.redistricting_project.Plans.Collections.SampleMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.text.NumberFormat;
import java.util.Locale;

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
        Plan plan = planRepository.findBy(state, shortenedReason, districtType);

        if (districtType.equals("mmd")) {
            List<Feature> updatedFeatures = updateFeatures(plan.getFeatures());
            plan.setFeatures(updatedFeatures);
        }
        return plan;
    }

    private String getShortenedReason(String reason) {
        Map<String, String> reasonMap = Map.of(
                "republican", "rep",
                "democratic", "dem",
                "opportunity-max", "op_max",
                "white-max", "wht_prob_max",
                "non-white-max", "non_wht_prob_max"
        );
        return reasonMap.get(reason);
    }

    private List<Feature> updateFeatures(List<Feature> features) {
        List<Feature> updatedFeatures = new ArrayList<>();

        for (Feature feature : features) {
            Property property = feature.getProperties();
            String partyWithVotes = generatePartyWithVotes(property);

            property.setWinningParty(partyWithVotes);
            updatedFeatures.add(feature);
        }
        return updatedFeatures;
    }

    private String generatePartyWithVotes(Property property) {
        String[] winningParty = property.getWinningParty().split(",");
        String[] winningVotes = property.getWinningPartyVotes().split(",");

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);

        return IntStream.range(0, winningParty.length)
                .mapToObj(i -> String.format("%s(%s)",
                        winningParty[i],
                        numberFormat.format(Integer.parseInt(winningVotes[i]))))
                .collect(Collectors.joining(", "));
    }

    public SampleMap getSampleMMDMap(String state) {
        SampleMap sampleMap = sampleMapRepository.findBy(state, "mmd");
        List<Feature> features = sampleMap.getFeatures();

        sampleMap.setFeatures(updateFeatures(features));
        return sampleMap;
    }

    public SampleMap getEnactedMap(String state) {
        return sampleMapRepository.findBy(state, "smd");
    }
}