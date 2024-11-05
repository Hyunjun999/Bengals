package com.bengals.redistricting_project.StateDistricts.Collections;

import com.bengals.redistricting_project.StateDistricts.StateDistrictReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StateDistrictListDTO {
    List<List<StateDistrictReqDTO>> all_districts;
}
