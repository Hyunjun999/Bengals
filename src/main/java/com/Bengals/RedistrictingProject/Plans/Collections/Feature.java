package com.Bengals.RedistrictingProject.Plans.Collections;

import lombok.Data;

@Data
public class Feature {
    private String type;
    private String districtId;
    private Property properties;
    private Geometry geometry;
}