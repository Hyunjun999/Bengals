package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summary {
    private int numDistPlan;
    private double averageMinMaxDifference;
    private double averageNonWhiteRepresentatives;
    private PartySplit averagePartySplit;
    private List<PartySplit> seatsVotes;
    private double bias;
    private double symmetry;
    private PartySplit responsiveness;
    private List<Integer> layout;
}
