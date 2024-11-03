package com.bengals.redistricting_project.StateDistricts.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StateDistrictDTO {
    @Id
    private ObjectId id;
    private String state;
    private String dis_type;
    private String type;
    private StateCrs crs;
    @JsonProperty("Republicans")
    private double Republicans;
    @JsonProperty("Democrats")
    private double Democrats;
    private double op_districts;
    private double op_threshold;
    private int safe_districts;
    private List<StateFeature> features;
}
