package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpRepresentatives {
    private List<OpRepresentativesElement> blk;
    private List<OpRepresentativesElement> asn;
    private List<OpRepresentativesElement> hsp;
    private List<OpRepresentativesElement> non_wht;
}
