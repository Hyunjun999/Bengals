package com.bengals.redistricting_project.Ensembles.Dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OpportunityDistrictsDto {
    List<OpportunityDistrictsElementDto> black;
    List<OpportunityDistrictsElementDto> asian;
    List<OpportunityDistrictsElementDto> hispanic;
    List<OpportunityDistrictsElementDto> nonWhite;
}
