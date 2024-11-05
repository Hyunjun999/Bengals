package com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BarDemocrats {
    private String name;
    @JsonProperty("Democrats")
    private double Democrats;
}
