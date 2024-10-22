package com.bengals.redistricting_project.StateDistricts.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "MSdistrict")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MSStateDistrict {
    @Id
    private ObjectId id;
    private String type;
    private String name;
    private StateCrs crs;
    private List<StateFeature> features;
}
