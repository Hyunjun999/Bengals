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
public class OpportunityRepRaceResDTO {
    List<OpportunityRepResCom> blk;
    List<OpportunityRepResCom> asn;
    List<OpportunityRepResCom> hsp;
    List<OpportunityRepResCom> non_wht;
}
