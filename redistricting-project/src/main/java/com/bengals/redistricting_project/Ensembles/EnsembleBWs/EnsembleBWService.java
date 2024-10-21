package com.bengals.redistricting_project.Ensembles.EnsembleBWs;

import com.bengals.redistricting_project.Ensembles.EnsembleBWs.Collections.EnsembleBW;
import org.springframework.stereotype.Service;

@Service
public class EnsembleBWService {
    private final EnsembleBWRepository ensembleBWRepository;

    public EnsembleBWService(EnsembleBWRepository ensembleBWRepository) {
        this.ensembleBWRepository = ensembleBWRepository;
    }

    public EnsembleBW findEnsembleBW(String state) {
        for (EnsembleBW ensembleBW : ensembleBWRepository.findAll()) {
            if (ensembleBW.getState().startsWith(state)) {
                return ensembleBW;
            }
        }
        return null;
    }
}
