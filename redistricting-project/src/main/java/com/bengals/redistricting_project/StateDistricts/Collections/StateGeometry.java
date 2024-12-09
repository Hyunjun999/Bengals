package com.bengals.redistricting_project.StateDistricts.Collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StateGeometry {
    private String type;
    private Object coordinates;
}
