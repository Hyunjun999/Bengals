package com.Bengals.RedistrictingProject.Plans.Collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import com.Bengals.RedistrictingProject.Ensembles.Collections.PartySplit;
import java.util.List;

@Document(collection = "RandomPlans")
@Data
public class Plan {
    private String state;
    private String districtType;
    private int totalPopulation;
    private int votePopulation;
    private int totalWhite;
    private int totalAsian;
    private int totalBlack;
    private int totalHispanic;
    private double nonWhiteProbability;
    private double whiteProbability;
    private int republican;
    private int democratic;
    private int numOpportunityDistricts;
    private int numSafeDistricts;
    private double opportunityThreshold;
    private String reason;
    private List<Feature> features;
    private List<PartySplit> seatsVotes;
    private double bias;
    private double symmetry;
    private PartySplit responsiveness;
}