package com.bengals.redistricting_project.State;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PartySplit {
    @JsonProperty("republicans")
    private int Republicans;

    @JsonProperty("democrats")
    private int Democrats;
}
