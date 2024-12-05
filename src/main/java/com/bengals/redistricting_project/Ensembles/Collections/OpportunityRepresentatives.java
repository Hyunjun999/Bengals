package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class OpportunityRepresentatives {
    private List<OpportunityRepresentativesElement> black;
    private List<OpportunityRepresentativesElement> asian;
    private List<OpportunityRepresentativesElement> hispanic;
    private List<OpportunityRepresentativesElement> nonWhite;
}
