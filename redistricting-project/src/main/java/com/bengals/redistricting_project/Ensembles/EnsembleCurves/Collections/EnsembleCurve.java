package com.bengals.redistricting_project.Ensembles.EnsembleCurves.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "EnsembleCurve")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnsembleCurve {
    private String state;
    private List<EnsembleParty> parties;
}
