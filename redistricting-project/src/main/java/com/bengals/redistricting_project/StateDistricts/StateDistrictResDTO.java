package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.StateCrs;
import com.bengals.redistricting_project.StateDistricts.Collections.StateFeatureReqDTO;
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
    private List<List<StateFeatureReqDTO>> features;
}