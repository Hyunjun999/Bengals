package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartySplitDistributionDto {
    private List<PartySplitDto> partySplitsBar;
    private PartySplit avgSeatShare;

    public static PartySplitDistributionDto toPartySplitDistributionDTO(Ensemble ensemble, String districtType) {
        PartySplitDistributionDto.PartySplitDistributionDtoBuilder partySplitDistributionDTOBuilder = PartySplitDistributionDto.builder();
        List<PartySplitDto> partySplitsBar = null;
        PartySplit avgSeatShare = null;
        if (districtType.equalsIgnoreCase("smd")) {
            partySplitsBar = ensemble.getSmd().getParty().getPartySplits();
            avgSeatShare = ensemble.getSmd().getParty().getAverageSeatShare();
        } else {
            partySplitsBar = ensemble.getMmd().getParty().getPartySplits();
            avgSeatShare = ensemble.getMmd().getParty().getAverageSeatShare();
        }
        return partySplitDistributionDTOBuilder.partySplitsBar(partySplitsBar).avgSeatShare(avgSeatShare).build();
    }
}
