package com.Bengals.RedistrictingProject.Ensembles.Collections;

import com.Bengals.RedistrictingProject.Ensembles.Dto.PartySplitDto;
import lombok.Data;
import java.util.List;

@Data
public class Party {
    private PartyBoxWhisker partyBoxWhisker;
    private List<PartySplitDto> partySplits;
    private PartySplit averageSeatShare;
}
