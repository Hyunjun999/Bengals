package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class RacialBoxWhisker {
    private List<BoxWhiskerElement> black;
    private List<BoxWhiskerElement> asian;
    private List<BoxWhiskerElement> hispanic;
    private List<BoxWhiskerElement> nonWhite;
}