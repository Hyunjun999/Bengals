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
public class Comparison {
    private double republican;
    private double democratic;
    private double numOpportunityRepresentatives;
    private List<PartySplit> seatsVotes;
}
