package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpDistricts {
    private List<OpDistrictsElement> blk;
    private List<OpDistrictsElement> hsp;
    private List<OpDistrictsElement> non_wht;
}
