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

public class OpportunityDistrictsDto {
    List<OpportunityDistrictsElementDto> black;
    List<OpportunityDistrictsElementDto> asian;
    List<OpportunityDistrictsElementDto> hispanic;
    List<OpportunityDistrictsElementDto> nonWhite;
}
