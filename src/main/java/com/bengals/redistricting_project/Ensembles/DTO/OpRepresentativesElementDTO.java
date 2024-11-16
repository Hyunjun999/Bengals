package com.bengals.redistricting_project.Ensembles.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpRepresentativesElementDTO {
    private int name;
    private int op_representatives;
}
