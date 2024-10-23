package com.bengals.redistricting_project.Ensembles.Collections;

import com.bengals.redistricting_project.StateDistricts.Collections.StateCrs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ALEnsemble")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ALEnsemble {
    @Id
    private ObjectId id;
    private String type;
    private StateCrs crs;
    private List<EnsembleFeature> features;
}
