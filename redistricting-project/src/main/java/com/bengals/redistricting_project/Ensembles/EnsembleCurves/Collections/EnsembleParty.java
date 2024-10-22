package com.bengals.redistricting_project.Ensembles.EnsembleCurves.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnsembleParty {
    private double republicans;
    private double democrats;
}
