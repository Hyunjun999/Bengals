package com.Bengals.RedistrictingProject.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class OpportunityDistricts {
    private List<OpportunityDistrictsElement> black;
    private List<OpportunityDistrictsElement> asian;
    private List<OpportunityDistrictsElement> hispanic;
    private List<OpportunityDistrictsElement> nonWhite;
}
