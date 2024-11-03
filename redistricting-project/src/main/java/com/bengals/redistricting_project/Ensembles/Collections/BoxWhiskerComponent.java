package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BoxWhiskerComponent {
    private int name;
    private double min;
    private double lowerQuartile;
    private double median;
    private double upperQuartile;
    private double max;
    private double average;
    private double enacted;
}
