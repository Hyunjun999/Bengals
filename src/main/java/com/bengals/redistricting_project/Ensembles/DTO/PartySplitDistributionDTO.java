package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import com.bengals.redistricting_project.Ensembles.Collections.PartySplit;
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
    private PartySplit avg_seat_share;

    public static PartySplitDistributionDTO toPartySplitDistributionDTO(Ensemble ensemble, String districtType) {
        PartySplitDistributionDTO.PartySplitDistributionDTOBuilder partySplitDistributionDTOBuilder = PartySplitDistributionDTO.builder();
        List<PartySplits> party_splits_dtos = null;
        PartySplit avg_seat_share = null;
        if (districtType.equalsIgnoreCase("smd")) {
            party_splits_dtos = ensemble.getSmd().getParty().getParty_splits();
            avg_seat_share = ensemble.getSmd().getParty().getAvg_seat_share();
        } else {
            party_splits_dtos = ensemble.getMmd().getParty().getParty_splits();
            avg_seat_share = ensemble.getMmd().getParty().getAvg_seat_share();
        }
        return partySplitDistributionDTOBuilder.party_splits_bar(party_splits_dtos).avg_seat_share(avg_seat_share).build();
    }
}
