package com.Bengals.RedistrictingProject.Ensembles.Collections;

import lombok.Data;
import java.util.List;

@Data
public class Party {
    private PartyBoxWhisker partyBoxWhisker;
    private List<PartySplitElement> partySplits;
    private PartySplit averageSeatShare;
}
