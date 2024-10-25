package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepository;
import com.bengals.redistricting_project.State.StateService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(EnsembleRepository ensembleRepository) {
        this.ensembleRepository = ensembleRepository;
    }

    public EnsembleDto findEnsemble(String state, String type) {
        Ensemble ensemble = ensembleRepository.findByStateAndType(state, type.toUpperCase());
        EnsembleDto ensembleDto = EnsembleDto.builder()
                .box_whisker(ensemble.getBox_whisker())
                .vote_seats(ensemble.getVote_seats())
                .build();
        return ensembleDto;
    }
}
