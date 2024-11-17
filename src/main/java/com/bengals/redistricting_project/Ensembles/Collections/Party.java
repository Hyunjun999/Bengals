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
public class Party {
    private PartyBoxWhisker party_box_whisker;
    private List<PartySplits> party_splits;
    private PartySplit avg_seat_share;
}
