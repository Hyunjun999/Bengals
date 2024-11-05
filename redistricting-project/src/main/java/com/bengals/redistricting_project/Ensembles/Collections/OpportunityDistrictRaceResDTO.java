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
public class OpportunityDistrictRaceResDTO {
    private List<OpportunityDistrictResCom> blk;
    private List<OpportunityDistrictResCom> asn;
    private List<OpportunityDistrictResCom> hsp;
    private List<OpportunityDistrictResCom> non_wht;
}
