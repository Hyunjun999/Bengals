package com.bengals.redistricting_project.Ensembles.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OpportunityRepresentativesElementDto {
    private int name;
    @JsonProperty("numberOfPlans")
    private int numOpRepresentatives;
}
