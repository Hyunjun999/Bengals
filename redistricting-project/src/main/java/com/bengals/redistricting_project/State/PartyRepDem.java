package com.bengals.redistricting_project.State;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PartyRepDem {

    @JsonProperty("republicans")
    private int Republicans;

    @JsonProperty("democrats")
    private int Democrats;
}
