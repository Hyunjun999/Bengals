package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class Comparison {
    private double republican;
    private double democratic;
    private double numOpportunityRepresentatives;
    private List<PartySplit> seatsVotes;
}
