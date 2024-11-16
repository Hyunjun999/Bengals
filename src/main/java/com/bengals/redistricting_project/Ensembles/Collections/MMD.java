package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MMD {
    private Summary summary;
    private Racial racial;
    private Party party;
    private EnactedAvg enacted_vs_avg;
}
