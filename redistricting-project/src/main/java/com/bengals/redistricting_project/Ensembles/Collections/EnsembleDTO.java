package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EnsembleDTO {
    private String state;
    private BoxWhisker box_whisker;
    private VoteSeats vote_seats;
    private PartySplits party_splits_bar;
    private OpportunityDistrictResDTO op_district_bar;
    private OpportunityRepResDTO op_representatives_bar;
}
