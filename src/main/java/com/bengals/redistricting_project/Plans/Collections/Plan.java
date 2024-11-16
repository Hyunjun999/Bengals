package com.bengals.redistricting_project.Plans.Collections;

import com.bengals.redistricting_project.Ensembles.Collections.MMD;
import com.bengals.redistricting_project.Ensembles.Collections.SMD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "RandomPlans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Plan {
    @Id
    private ObjectId id;
    private String state;
    private String district_type;
    private int republican;
    private int democratic;
    private int num_op_districts;
    private int num_safe_districts;
    private double op_threshold;
    private String reason;
    private List<Feature> features;
}
