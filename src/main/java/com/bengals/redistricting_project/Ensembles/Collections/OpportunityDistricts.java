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
public class OpportunityDistricts {
    private List<OpportunityDistrictsElement> blk;
    private List<OpportunityDistrictsElement> asn;
    private List<OpportunityDistrictsElement> hsp;
    private List<OpportunityDistrictsElement> nonWht;
}
