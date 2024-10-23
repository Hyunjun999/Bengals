package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.ALEnsemble;
import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import com.bengals.redistricting_project.Ensembles.Collections.MSEnsemble;
import com.bengals.redistricting_project.Ensembles.Collections.PAEnsemble;
import com.bengals.redistricting_project.Ensembles.Repository.ALEnsembleRepository;
import com.bengals.redistricting_project.Ensembles.Repository.MSEnsembleRepository;
import com.bengals.redistricting_project.Ensembles.Repository.PAEnsembleRepository;
import org.springframework.stereotype.Service;

@Service
public class EnsembleService {
    private final MSEnsembleRepository msEnsembleRepository;
    private final ALEnsembleRepository alEnsembleRepository;
    private final PAEnsembleRepository paEnsembleRepository;

    public EnsembleService(MSEnsembleRepository msEnsembleRepository,
                           ALEnsembleRepository alEnsembleRepository,
                           PAEnsembleRepository paEnsembleRepository) {
        this.msEnsembleRepository = msEnsembleRepository;
        this.alEnsembleRepository = alEnsembleRepository;
        this.paEnsembleRepository = paEnsembleRepository;
    }

    public Ensemble findEnsemble(String state) {
        Ensemble ensemble = new Ensemble();
        if (state.equals("AL")) {
            ALEnsemble alEnsemble = alEnsembleRepository.findAll().get(0);
            ensemble = Ensemble.builder()
                    .id(alEnsemble.getId())
                    .type(alEnsemble.getType())
                    .crs(alEnsemble.getCrs())
                    .features(alEnsemble.getFeatures()).
                    build();
        } else if (state.equals("PA")) {
            paEnsembleRepository.findAll();
            PAEnsemble paEnsemble = paEnsembleRepository.findAll().get(0);
            ensemble = Ensemble.builder()
                    .id(paEnsemble.getId())
                    .type(paEnsemble.getType())
                    .crs(paEnsemble.getCrs())
                    .features(paEnsemble.getFeatures()).
                    build();
        } else if (state.equals("MS")) {
            msEnsembleRepository.findAll();
            MSEnsemble msEnsemble = msEnsembleRepository.findAll().get(0);
            ensemble = Ensemble.builder()
                    .id(msEnsemble.getId())
                    .type(msEnsemble.getType())
                    .crs(msEnsemble.getCrs())
                    .features(msEnsemble.getFeatures()).
                    build();
        }
        return ensemble;
    }
}
