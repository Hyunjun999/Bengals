package com.bengals.redistricting_project.State;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PartySplit {
    @JsonProperty("ensemble")
    private int Ensemble;

    @JsonProperty("random")
    private int Random;
}
