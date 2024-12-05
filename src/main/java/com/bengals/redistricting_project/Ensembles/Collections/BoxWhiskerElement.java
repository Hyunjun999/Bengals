package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;

@Data
public class BoxWhiskerElement {
    private int name;
    private double min;
    private double lowerQuartile;
    private double median;
    private double upperQuartile;
    private double max;
    private double average;
    private double enacted;
}
