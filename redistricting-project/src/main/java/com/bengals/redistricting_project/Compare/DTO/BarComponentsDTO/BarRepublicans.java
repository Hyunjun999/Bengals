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

public class BarRepublicans {
    private String name;
    @JsonProperty("Republicans")
    private double Republicans;
}
