package com.bengals.redistricting_project.Ensembles.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityRepresentativesDto {
    private List<OpportunityRepresentativesElementDto> blk;
    private List<OpportunityRepresentativesElementDto> asn;
    private List<OpportunityRepresentativesElementDto> hsp;
    private List<OpportunityRepresentativesElementDto> nonWht;
}
