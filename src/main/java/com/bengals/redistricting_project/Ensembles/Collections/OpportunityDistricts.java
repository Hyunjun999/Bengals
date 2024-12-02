package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpportunityDistricts {
    private List<OpportunityDistrictsElement> black;
    private List<OpportunityDistrictsElement> asian;
    private List<OpportunityDistrictsElement> hispanic;
    private List<OpportunityDistrictsElement> nonWhite;
}
