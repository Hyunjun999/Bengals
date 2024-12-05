package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class PartyBoxWhisker {
    private List<BoxWhiskerElement> republican;
    private List<BoxWhiskerElement> democratic;
}
