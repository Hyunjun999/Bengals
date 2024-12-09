package com.bengals.redistricting_project.Compare.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentSMDReqDTO {
    @JsonProperty("Republicans")
    private double Republicans;
    @JsonProperty("Democrats")
    private double Democrats;
    private int op_districts;
    private int op_representatives;
}
