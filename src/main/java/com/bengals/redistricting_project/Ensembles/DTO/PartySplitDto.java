package com.bengals.redistricting_project.Ensembles.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartySplitDto {
    private int name;
    private int republican;
    private int democratic;
}
