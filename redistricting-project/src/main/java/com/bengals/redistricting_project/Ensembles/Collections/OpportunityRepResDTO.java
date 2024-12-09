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
public class OpportunityRepResDTO {
        @JsonProperty("SMD")
        private OpportunityRepRaceResDTO SMD;
        @JsonProperty("MMD")
        private OpportunityRepRaceResDTO MMD;
}
