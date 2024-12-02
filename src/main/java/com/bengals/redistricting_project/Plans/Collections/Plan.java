package com.bengals.redistricting_project.Plans.Collections;

import com.bengals.redistricting_project.Ensembles.Collections.MMD;
import com.bengals.redistricting_project.Ensembles.Collections.PartySplit;
import com.bengals.redistricting_project.Ensembles.Collections.SMD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "RandomPlans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

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
