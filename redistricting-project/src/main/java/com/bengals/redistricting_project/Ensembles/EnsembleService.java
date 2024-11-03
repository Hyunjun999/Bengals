package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(EnsembleRepository ensembleRepository) {
        this.ensembleRepository = ensembleRepository;
    }

    public EnsembleDTO findEnsemble(String state) {
        Ensemble ensemble = ensembleRepository.findByState(state);

        EnsembleDTO ensembleDTO = EnsembleDTO.builder()
                .state(ensemble.getState())
                .box_whisker(ensemble.getBox_whisker())
                .vote_seats(ensemble.getVote_seats())
                .party_splits_bar(ensemble.getParty_splits_bar())
                .op_district_bar(ensemble.getOp_district_bar())
                .op_representatives_bar(ensemble.getOp_representatives_bar())
                .build();

        return ensembleDTO;
    }
}