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
public class OpportunityRepresentatives {
    private List<OpportunityRepresentativesElement> blk;
    private List<OpportunityRepresentativesElement> asn;
    private List<OpportunityRepresentativesElement> hsp;
    private List<OpportunityRepresentativesElement> nonWht;
}
