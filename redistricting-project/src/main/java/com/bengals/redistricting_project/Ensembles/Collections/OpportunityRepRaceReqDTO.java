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
public class OpportunityRepRaceReqDTO {
    List<OpportunityRepReqCom> blk;
    List<OpportunityRepReqCom> asn;
    List<OpportunityRepReqCom> hsp;
    List<OpportunityRepReqCom> non_wht;
}
