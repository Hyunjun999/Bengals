package com.bengals.redistricting_project.StateDistricts.Collections;

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
public class StateDistrictDto {
    @Id
    private ObjectId id;
    private String type;
    private int plan_id;
    private List<StateFeature> features;
}
