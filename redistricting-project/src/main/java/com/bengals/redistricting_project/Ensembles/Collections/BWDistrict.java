package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BWDistrict {
    private String name;
    private double min;
    private double lowerQuartile;
    private double median;
    private double upperQuartile;
    private double max;
    private double average;
}
