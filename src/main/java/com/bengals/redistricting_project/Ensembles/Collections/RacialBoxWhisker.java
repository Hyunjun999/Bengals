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
public class RacialBoxWhisker {
    private List<BoxWhiskerElement> black;
    private List<BoxWhiskerElement> asian;
    private List<BoxWhiskerElement> hispanic;
    private List<BoxWhiskerElement> nonWhite;
}
