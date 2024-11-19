package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.EnactedAvg;
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
        if (districtType.equalsIgnoreCase("smd")) return ensembleRepository.findByState(state).getSmd().getRacial().getRacial_box_whisker();
        else return ensembleRepository.findByState(state).getMmd().getRacial().getRacial_box_whisker();
    }

    public RacialOpportunityDTO getRacialOpportunity(String state, String districtType) {
        return RacialOpportunityDTO.toRacialOpportunityDTO(ensembleRepository.findByState(state), districtType);
    }

    public PartyBoxWhisker getPartyPopDistribution(String state, String districtType) {
        if (districtType.equalsIgnoreCase("smd")) return ensembleRepository.findByState(state).getSmd().getParty().getParty_box_whisker();
        else return ensembleRepository.findByState(state).getMmd().getParty().getParty_box_whisker();
    }

    public PartySplitDistributionDTO getPartySplitDistribution(String state, String districtType) {
        return PartySplitDistributionDTO.toPartySplitDistributionDTO(ensembleRepository.findByState(state), districtType);
    }

    public EnactedAvg getComparisonEnactedAvg(String state) {
        return ensembleRepository.findByState(state).getMmd().getEnacted_vs_avg();
    }

}
