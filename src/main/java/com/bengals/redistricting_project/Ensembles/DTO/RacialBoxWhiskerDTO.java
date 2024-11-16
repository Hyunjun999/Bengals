package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RacialBoxWhiskerDTO {
    private RacialBoxWhisker racial_box_whisker;
    public static RacialBoxWhiskerDTO toRacialBoxWhiskerDTO(Ensemble ensemble, String districtType) {
        RacialBoxWhiskerDTO.RacialBoxWhiskerDTOBuilder racialBoxWhiskerDTOBuilder = RacialBoxWhiskerDTO.builder();
        Racial racial = null;
        if (districtType.equalsIgnoreCase("smd")) {
            racial = ensemble.getSmd().getRacial();
        } else {
            racial = ensemble.getMmd().getRacial();
        }
        return racialBoxWhiskerDTOBuilder.racial_box_whisker(racial.getRacial_box_whisker()).build();
    }
}
