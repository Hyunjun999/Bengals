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
    private List<OpportunityDistrictResDTO> blk;
    private List<OpportunityDistrictResDTO> asn;
    private List<OpportunityDistrictResDTO> hsp;
    private List<OpportunityDistrictResDTO> non_wht;
}
