package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateCrs;
import com.bengals.redistricting_project.StateDistricts.Collections.StateFeatureReqDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StateDistrictResDTO {
    @Id
    private ObjectId id;
    private String type;
    private StateCrs crs;
    private String district_id;
    private String state;
    private String dis_type;
    @JsonProperty("Republicans")
    private int Republicans;
    @JsonProperty("Democrats")
    private int Democrats;
    private int op_districts;
    private int safe_districts;
    private double op_threshold;
    private List<StateFeatureReqDTO> features;
}