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

public class VoteSeats {
    @JsonProperty("Republicans")
    private double Republicans;
    @JsonProperty("Democrats")
    private double Democrats;
}
