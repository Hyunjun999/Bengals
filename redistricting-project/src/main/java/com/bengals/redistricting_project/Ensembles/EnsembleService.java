package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepositoryMMD;
import com.bengals.redistricting_project.Ensembles.Repository.EnsembleRepositorySMD;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnsembleService {
    private final EnsembleRepositorySMD ensembleRepositorySMD;
    private final EnsembleRepositoryMMD ensembleRepositoryMMD;

    public EnsembleService(
            EnsembleRepositorySMD ensembleRepositorySMD,
            EnsembleRepositoryMMD ensembleRepositoryMMD) {
        this.ensembleRepositorySMD = ensembleRepositorySMD;
        this.ensembleRepositoryMMD = ensembleRepositoryMMD;
    }

    public EnsembleDto findEnsembleSMD(String state) {
        EnsembleSMD ensembleSMD = null;
        Optional<EnsembleSMD> optional = Optional.empty();
        EnsembleDto ensembleDto = null;
        String objectID = "";

        if (state.equals("AL")) {
            objectID = "671ab949e69bb8486e14c486";
        } else if (state.equals("PA")) {
            objectID = "";
        } else if (state.equals("MS")) {
            objectID = "671ab5fde69bb8486e14c47b";
        }
        optional = ensembleRepositorySMD.findById(new ObjectId(objectID));
        if (optional.isPresent()) ensembleSMD = optional.get();
        ensembleDto = EnsembleDto.builder()
                .box_whisker(ensembleSMD.getBox_whisker())
                .vote_seats(ensembleSMD.getVote_seats())
                .build();

        return ensembleDto;
    }

    public EnsembleDto findEnsembleMMD(String state) {
        EnsembleMMD ensembleMMD = null;
        Optional<EnsembleMMD> optional = Optional.empty();
        EnsembleDto ensembleDto = null;
        String objectID = "";

        if (state.equals("AL")) {
            objectID = "671bb1c0a616db0d6aefe5e0";
        } else if (state.equals("PA")) {
            objectID = "";
        } else if (state.equals("MS")) {
            objectID = "671bb1b1a616db0d6aefe5df";
        }
        optional = ensembleRepositoryMMD.findById(new ObjectId(objectID));
        if (optional.isPresent()) ensembleMMD = optional.get();
        ensembleDto = EnsembleDto.builder()
                .box_whisker(ensembleMMD.getBox_whisker())
                .vote_seats(ensembleMMD.getVote_seats())
                .build();

        return ensembleDto;
    }
}
