package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.EnactedAverage;
import com.bengals.redistricting_project.Ensembles.Collections.PartyBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.RacialBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.Summary;
import com.bengals.redistricting_project.Ensembles.Dto.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnsembleController {
    private final EnsembleService ensembleService;

    public EnsembleController(EnsembleService ensembleService) {
        this.ensembleService = ensembleService;
    }

    @GetMapping("/{state}/ensemble-summary/{districtType}")
    public Summary getEnsembleSummary(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getEnsembleSummary(state, districtType);
    }

    @GetMapping("/{state}/racial-distribution/{districtType}")
    public RacialBoxWhisker getRacialDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getRacialDistribution(state, districtType);
    }

    @GetMapping("/{state}/opportunity-distribution/{districtType}")
    public RacialOpportunityDto getOpportunityDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getOpportunityDistribution(state, districtType);
    }

    @GetMapping("/{state}/party-population-distribution/{districtType}")
    public PartyBoxWhisker getPartyPopulationDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getPartyPopulationDistribution(state, districtType);
    }

    @GetMapping("/{state}/party-split-distribution/{districtType}")
    public PartySplitDistributionDto getPartySplitDistribution(@PathVariable String state, @PathVariable String districtType) {
        return ensembleService.getPartySplitDistribution(state, districtType);
    }

    @GetMapping("/{state}/plan-comparison")
    public EnactedAverage getComparison(@PathVariable String state) {
        return ensembleService.getComparison(state);
    }
}
