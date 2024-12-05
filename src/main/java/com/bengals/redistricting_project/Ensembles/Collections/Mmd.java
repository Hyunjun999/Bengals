package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;

@Data
public class Mmd {
    private Summary summary;
    private Racial racial;
    private Party party;
    private EnactedAverage enactedAverage;
}
