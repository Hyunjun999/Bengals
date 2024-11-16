package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import com.bengals.redistricting_project.Ensembles.Collections.PartyBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.PartySplits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartySplitDistributionDTO {
private List<PartySplits> party_splits_bar;

    public static PartySplitDistributionDTO toPartySplitDistributionDTO(Ensemble ensemble, String districtType) {
        PartySplitDistributionDTO.PartySplitDistributionDTOBuilder partySplitDistributionDTOBuilder = PartySplitDistributionDTO.builder();
        List<PartySplits> party_splits = null;
        if (districtType.equalsIgnoreCase("smd")) {
            party_splits = ensemble.getSmd().getParty().getParty_splits();
        } else {
            party_splits = ensemble.getMmd().getParty().getParty_splits();
        }
        return partySplitDistributionDTOBuilder.party_splits_bar(party_splits).build();
    }
}
