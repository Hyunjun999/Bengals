package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDistrictRaceReqDTO {
    private List<OpportunityDistrictReqCom> blk;
    private List<OpportunityDistrictReqCom> asn;
    private List<OpportunityDistrictReqCom> hsp;
    private List<OpportunityDistrictReqCom> non_wht;
}
