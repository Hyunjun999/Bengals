package com.bengals.redistricting_project.Ensembles.Collections;

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
    private List<VoteSeatsPartySplitsComponent> SMD;
    private List<VoteSeatsPartySplitsComponent> MMD;
    private DistrictType symmetry;
    private DistrictType bias;
    private Responsiveness responsiveness;

    public static class DistrictType {
        public double SMD;
        public double MMD;
    }
}