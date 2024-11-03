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
    private List<OpportunityDistrictReqDTO> blk;
    private List<OpportunityDistrictReqDTO> asn;
    private List<OpportunityDistrictReqDTO> hsp;
    private List<OpportunityDistrictReqDTO> non_wht;
}
