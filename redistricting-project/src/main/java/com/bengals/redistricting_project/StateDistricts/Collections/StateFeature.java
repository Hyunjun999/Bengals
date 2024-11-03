package com.bengals.redistricting_project.StateDistricts.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StateFeature {
    private String type;
    private StateProperties properties;
    private StateGeometry geometry;
}
