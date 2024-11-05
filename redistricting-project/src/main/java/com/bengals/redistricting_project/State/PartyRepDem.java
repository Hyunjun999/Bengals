package com.bengals.redistricting_project.State;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PartyRepDem {

    @JsonProperty("Republicans")
    private double Republicans;

    @JsonProperty("Democrats")
    private double Democrats;
}
