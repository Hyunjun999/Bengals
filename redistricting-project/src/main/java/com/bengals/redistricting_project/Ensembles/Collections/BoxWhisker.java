package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoxWhisker {
    private List<BoxWhiskerComponent> box_SMD;
    private List<BoxWhiskerComponent> box_MMD;
}