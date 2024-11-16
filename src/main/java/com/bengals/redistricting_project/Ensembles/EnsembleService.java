package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.DTO.*;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepository;
import org.springframework.stereotype.Service;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(EnsembleRepository ensembleRepository) {
        this.ensembleRepository = ensembleRepository;
    }

    public EnsembleSummaryDTO getEnsembleSummary(String state, String districtType) {
        return EnsembleSummaryDTO.toEnsembleSummaryDTO(ensembleRepository.findByState(state), districtType);
    }

    public RacialBoxWhiskerDTO getRacialDistribution(String state, String districtType) {
        return RacialBoxWhiskerDTO.toRacialBoxWhiskerDTO(ensembleRepository.findByState(state), districtType);
    }

    public RacialOpportunityDTO getRacialOpportunity(String state, String districtType) {
        return RacialOpportunityDTO.toRacialOpportunityDTO(ensembleRepository.findByState(state), districtType);
    }

    public PartyPopDistributionDTO getPartyPopDistribution(String state, String districtType) {
        return PartyPopDistributionDTO.toPartyPopDistributionDTO(ensembleRepository.findByState(state), districtType);
    }

    public PartySplitDistributionDTO getPartySplitDistribution(String state, String districtType){
        return PartySplitDistributionDTO.toPartySplitDistributionDTO(ensembleRepository.findByState(state), districtType);
    }

    public EnactedAvgDTO getComparisonEnactedAvg(String state){
        return EnactedAvgDTO.toEnactedAvgDTO(ensembleRepository.findByState(state));
    }

}
