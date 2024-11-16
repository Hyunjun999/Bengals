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
    private int num_dist_plan;
    private double avg_min_max_diff;
    private MinorRep avg_num_minor_representatives;
    private PartySplit avg_party_split;
    private List<PartySplit> seats_votes;
    private double bias;
    private double symmetry;
    private PartySplit responsiveness;
}
