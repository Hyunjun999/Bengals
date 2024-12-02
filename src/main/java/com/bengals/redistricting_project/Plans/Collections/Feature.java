package com.bengals.redistricting_project.Plans.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feature {
    private String type;
    private String districtId;
    private Property properties;
    private Geometry geometry;
}
