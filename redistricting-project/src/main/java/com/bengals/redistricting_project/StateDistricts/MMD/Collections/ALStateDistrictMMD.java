package com.bengals.redistricting_project.StateDistricts.MMD.Collections;

import com.bengals.redistricting_project.StateDistricts.Collections.StateCrs;
import com.bengals.redistricting_project.StateDistricts.Collections.StateFeature;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "ALdistrictMMD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ALStateDistrictMMD {
    @Id
    private ObjectId id;
    private String type;
    private String name;
    private StateCrs stateCrs;
    private List<StateFeature> features;
}
