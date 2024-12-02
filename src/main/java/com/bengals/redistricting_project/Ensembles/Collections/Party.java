package com.bengals.redistricting_project.Ensembles.Collections;

import com.bengals.redistricting_project.Ensembles.DTO.PartySplitDto;
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
    private PartyBoxWhisker partyBoxWhisker;
    private List<PartySplitDto> partySplits;
    private PartySplit averageSeatShare;
}
