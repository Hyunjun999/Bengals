package com.bengals.redistricting_project.Ensembles.Dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OpportunityRepresentativesDto {
    private List<OpportunityRepresentativesElementDto> black;
    private List<OpportunityRepresentativesElementDto> asian;
    private List<OpportunityRepresentativesElementDto> hispanic;
    private List<OpportunityRepresentativesElementDto> nonWhite;
}
