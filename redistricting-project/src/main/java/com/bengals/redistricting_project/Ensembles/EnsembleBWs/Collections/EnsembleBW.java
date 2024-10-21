package com.bengals.redistricting_project.Ensembles.EnsembleBWs.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "EnsembleBoxWhisker")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EnsembleBW {
    @Id
    private ObjectId id;
    private String state;
    private List<EnsembleDistrict> districts;
}
