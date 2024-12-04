package com.Bengals.RedistrictingProject.Ensembles.Dto;

import com.Bengals.RedistrictingProject.Ensembles.Collections.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PartySplitDistributionDto {
    private List<PartySplitDto> partySplitsBar;
    private PartySplit averageSeatShare;

    public static PartySplitDistributionDto toPartySplitDistributionDto(Ensemble ensemble, String districtType) {
        Party party = districtType.equals("smd") ? ensemble.getSmd().getParty() : ensemble.getMmd().getParty();

        return PartySplitDistributionDto.builder()
                .partySplitsBar(party.getPartySplits())
                .averageSeatShare(party.getAverageSeatShare())
                .build();
    }
}