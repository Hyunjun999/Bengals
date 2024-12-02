package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Racial {
    private RacialBoxWhisker racialBoxWhisker;
    private OpportunityDistricts opportunityDistricts;
    private OpportunityRepresentatives opportunityRepresentatives;
}
