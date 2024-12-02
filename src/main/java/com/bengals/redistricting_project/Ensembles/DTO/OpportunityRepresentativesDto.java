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
    private List<OpportunityRepresentativesElementDto> black;
    private List<OpportunityRepresentativesElementDto> asian;
    private List<OpportunityRepresentativesElementDto> hispanic;
    private List<OpportunityRepresentativesElementDto> nonWhite;
}
