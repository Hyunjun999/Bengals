package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ensembles")
@Data
public class Ensemble {
    private String state;
    private Smd smd;
    private Mmd mmd;
}