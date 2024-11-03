package com.bengals.redistricting_project.Ensembles.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class VoteSeats {
    @JsonProperty("SMD")
    private List<VoteSeatsPartySplitsComponent> SMD;
    @JsonProperty("MMD")
    private List<VoteSeatsPartySplitsComponent> MMD;
    private DistrictType symmetry;
    private DistrictType bias;
    private Responsiveness responsiveness;

    public static class DistrictType {
        public double SMD;
        public double MMD;
    }
}