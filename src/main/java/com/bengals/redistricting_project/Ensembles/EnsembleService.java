package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.EnactedAverage;
import com.bengals.redistricting_project.Ensembles.Collections.PartyBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.RacialBoxWhisker;
import com.bengals.redistricting_project.Ensembles.Collections.Summary;
import com.bengals.redistricting_project.Ensembles.Dto.*;
import org.springframework.stereotype.Service;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(EnsembleRepository ensembleRepository) {
        this.ensembleRepository = ensembleRepository;
    }

    public Summary getEnsembleSummary(String state, String districtType) {
        if (districtType.equals("smd")) return ensembleRepository.findByState(state).getSmd().getSummary();
        else return ensembleRepository.findByState(state).getMmd().getSummary();
    }

    public RacialBoxWhisker getRacialDistribution(String state, String districtType) {
        if (districtType.equals("smd")) return ensembleRepository.findByState(state).getSmd().getRacial().getRacialBoxWhisker();
        else return ensembleRepository.findByState(state).getMmd().getRacial().getRacialBoxWhisker();
    }

    public RacialOpportunityDto getOpportunityDistribution(String state, String districtType) {
        return RacialOpportunityDto.toRacialOpportunityDto(ensembleRepository.findByState(state), districtType);
    }

    public PartyBoxWhisker getPartyPopulationDistribution(String state, String districtType) {
        if (districtType.equals("smd")) return ensembleRepository.findByState(state).getSmd().getParty().getPartyBoxWhisker();
        else return ensembleRepository.findByState(state).getMmd().getParty().getPartyBoxWhisker();
    }

    public PartySplitDistributionDto getPartySplitDistribution(String state, String districtType) {
        return PartySplitDistributionDto.toPartySplitDistributionDto(ensembleRepository.findByState(state), districtType);
    }

    public EnactedAverage getComparison(String state) {
        return ensembleRepository.findByState(state).getMmd().getEnactedAverage();
    }

}
