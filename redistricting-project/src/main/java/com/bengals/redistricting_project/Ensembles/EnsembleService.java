package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnsembleService {
    private final EnsembleRepository ensembleRepository;

    public EnsembleService(
            EnsembleRepository ensembleRepository
    ) {
        this.ensembleRepository = ensembleRepository;
    }

    public EnsembleDto findEnsemble(String state) {
        Ensemble ensemble = null;
        Optional<Ensemble> optional = Optional.empty();
        EnsembleDto ensembleDto = null;
        String objectID = "";

        if (state.equals("AL")) {
            objectID = "";

        } else if (state.equals("PA")) {
            objectID = "671ab949e69bb8486e14c486";
        } else if (state.equals("MS")) {
            objectID = "671ab5fde69bb8486e14c47b";
        }

        optional = ensembleRepository.findById(new ObjectId(objectID));
        if (optional.isPresent()) ensemble = optional.get();
        ensembleDto = EnsembleDto.builder()
                .box_whisker(ensemble.getBox_whisker())
                .vote_seats(ensemble.getVote_seats())
                .build();

        return ensembleDto;
    }
}
