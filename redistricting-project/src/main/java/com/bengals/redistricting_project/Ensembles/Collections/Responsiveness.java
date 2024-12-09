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
public class Responsiveness {
    @JsonProperty("SMD")
    private VoteSeatsPartySplitsComponent SMD;
    @JsonProperty("MMD")
    private VoteSeatsPartySplitsComponent MMD;
}