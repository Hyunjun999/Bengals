package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class Summary {
    private int numDistrictPlan;
    private double averageMinMaxDifference;
    private double averageNonWhiteRepresentatives;
    private PartySplit averagePartySplit;
    private List<PartySplit> seatsVotes;
    private double bias;
    private double symmetry;
    private PartySplit responsiveness;
    private List<Integer> layout;
}
