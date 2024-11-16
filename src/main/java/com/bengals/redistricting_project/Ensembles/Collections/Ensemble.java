package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Ensembles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Ensemble {
    @Id
    private ObjectId id;
    private String state;
    private SMD smd;
    private MMD mmd;
}