package com.bengals.redistricting_project.Ensembles.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PartySplitElement {
    private String name;
    @JsonProperty("numberOfPlans")
    private int value;
}