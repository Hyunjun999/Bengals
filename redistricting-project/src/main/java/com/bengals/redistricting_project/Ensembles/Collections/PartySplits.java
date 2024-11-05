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
public class PartySplits {
    @JsonProperty("SMD")
    private List<PartySplitsBarComponent> SMD;
    @JsonProperty("MMD")
    private List<PartySplitsBarComponent> MMD;
}