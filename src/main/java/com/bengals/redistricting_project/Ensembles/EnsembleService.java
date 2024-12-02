package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.EnactedAverage;
import com.bengals.redistricting_project.Ensembles.Collections.PartyBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.RacialBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.Summary;
import com.bengals.redistricting_project.Ensembles.DTO.*;
import org.springframework.stereotype.Service;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(EnsembleRepository ensembleRepository) {
        this.ensembleRepository = ensembleRepository;
    }

    public Summary getEnsembleSummary(String state, String districtType) {
        if (districtType.equalsIgnoreCase("smd")) return ensembleRepository.findByState(state).getSmd().getSummary();
        else return ensembleRepository.findByState(state).getMmd().getSummary();
    }

    public RacialBoxWhisker getRacialDistribution(String state, String districtType) {
        if (districtType.equalsIgnoreCase("smd")) return ensembleRepository.findByState(state).getSmd().getRacial().getRacialBoxWhisker();
        else return ensembleRepository.findByState(state).getMmd().getRacial().getRacialBoxWhisker();
    }

    public RacialOpportunityDto getRacialOpportunity(String state, String districtType) {
        return RacialOpportunityDto.toRacialOpportunityDTO(ensembleRepository.findByState(state), districtType);
    }

    public PartyBoxWhisker getPartyPopDistribution(String state, String districtType) {
        if (districtType.equalsIgnoreCase("smd")) return ensembleRepository.findByState(state).getSmd().getParty().getPartyBoxWhisker();
        else return ensembleRepository.findByState(state).getMmd().getParty().getPartyBoxWhisker();
    }

    public PartySplitDistributionDto getPartySplitDistribution(String state, String districtType) {
        return PartySplitDistributionDto.toPartySplitDistributionDTO(ensembleRepository.findByState(state), districtType);
    }

    public EnactedAverage getComparisonEnactedAvg(String state) {
        return ensembleRepository.findByState(state).getMmd().getEnactedAverage();
    }

}
