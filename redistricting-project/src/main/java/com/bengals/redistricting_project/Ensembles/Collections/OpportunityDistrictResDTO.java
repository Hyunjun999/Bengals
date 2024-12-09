package com.bengals.redistricting_project.Ensembles.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDistrictResDTO {
    @JsonProperty("SMD")
    private OpportunityDistrictRaceResDTO SMD;
    @JsonProperty("MMD")
    private OpportunityDistrictRaceResDTO MMD;
}
