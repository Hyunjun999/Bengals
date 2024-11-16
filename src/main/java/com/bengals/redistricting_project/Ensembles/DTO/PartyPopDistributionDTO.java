package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.BoxWhiskerElement;
import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import com.bengals.redistricting_project.Ensembles.Collections.Party;
import com.bengals.redistricting_project.Ensembles.Collections.PartyBoxWhisker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartyPopDistributionDTO {
    private List<BoxWhiskerElement> republican;
    private List<BoxWhiskerElement> democratic;

    public static PartyPopDistributionDTO toPartyPopDistributionDTO(Ensemble ensemble, String districtType) {
        PartyPopDistributionDTO.PartyPopDistributionDTOBuilder partyPopDistributionDTOBuilder = PartyPopDistributionDTO.builder();
        PartyBoxWhisker partyBoxWhisker = null;
        if (districtType.equalsIgnoreCase("smd")) {
            partyBoxWhisker = ensemble.getSmd().getParty().getParty_box_whisker();
        } else {
            partyBoxWhisker = ensemble.getMmd().getParty().getParty_box_whisker();
        }
        return partyPopDistributionDTOBuilder.republican(partyBoxWhisker.getRepublican()).democratic(partyBoxWhisker.getDemocratic()).build();
    }
}
