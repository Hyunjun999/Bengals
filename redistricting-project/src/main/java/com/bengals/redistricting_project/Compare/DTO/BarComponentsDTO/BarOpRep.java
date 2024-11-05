package com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BarOpRep {
    private String name;
    private double op_representatives;
}
