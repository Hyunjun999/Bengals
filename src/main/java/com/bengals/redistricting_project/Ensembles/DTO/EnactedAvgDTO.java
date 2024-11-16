package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.Comparison;
import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnactedAvgDTO {
    private Comparison enacted;
    private Comparison avg_mmd;

    public static EnactedAvgDTO toEnactedAvgDTO(Ensemble ensemble) {
        EnactedAvgDTO.EnactedAvgDTOBuilder enactedAvgDTOBuilder = EnactedAvgDTO.builder();
        return enactedAvgDTOBuilder
                .enacted(ensemble.getMmd().getEnacted_vs_avg().getEnacted())
                .avg_mmd((ensemble.getMmd().getEnacted_vs_avg().getAvg_mmd())).build();
    }
}
