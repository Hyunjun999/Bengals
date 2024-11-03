package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDistrict {
    private OpportunityDistrictRaceReqDTO SMD;
    private OpportunityDistrictRaceReqDTO MMD;
}
