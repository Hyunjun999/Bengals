package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.PartyBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.RacialBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.Summary;
import com.bengals.redistricting_project.Ensembles.DTO.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnsembleController {
    private final EnsembleService ensembleService;

    public EnsembleController(EnsembleService ensembleService) {
        this.ensembleService = ensembleService;
    }

    @GetMapping("/{state}/ensembleSummary/{districtType}")
    public Summary getEnsembleSummary(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getEnsembleSummary(state, districtType.toLowerCase());
    }

    @GetMapping("/{state}/racialDistribution/{districtType}")
    public RacialBoxWhisker getRacialDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getRacialDistribution(state, districtType.toLowerCase());
    }

    @GetMapping("/{state}/opDistribution/{districtType}")
    public RacialOpportunityDTO getRacialOpportunity(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getRacialOpportunity(state, districtType.toLowerCase());
    }

    @GetMapping("/{state}/partyPopDistribution/{districtType}")
    public PartyBoxWhisker getPartyPopDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getPartyPopDistribution(state, districtType.toLowerCase());
    }

    @GetMapping("/{state}/partySplitDistribution/{districtType}")
    public PartySplitDistributionDTO getPartySplitDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getPartySplitDistribution(state, districtType.toLowerCase());
    }

    @GetMapping("/{state}/planComparison")
    public EnactedAvgDTO getComparisonEnactedAvg(@PathVariable String state) {
        return ensembleService.getComparisonEnactedAvg(state);
    }
}
