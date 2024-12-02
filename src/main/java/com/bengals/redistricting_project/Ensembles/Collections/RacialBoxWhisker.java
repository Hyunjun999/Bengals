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
    private List<BoxWhiskerElement> blk;
    private List<BoxWhiskerElement> asn;
    private List<BoxWhiskerElement> hsp;
    private List<BoxWhiskerElement> nonWht;
}
