package com.bengals.redistricting_project.Ensembles.Collections;

import com.bengals.redistricting_project.StateDistricts.Collections.StateCrsProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EnsembleCrs {
    private String type;
    private StateCrsProperties properties;
}
