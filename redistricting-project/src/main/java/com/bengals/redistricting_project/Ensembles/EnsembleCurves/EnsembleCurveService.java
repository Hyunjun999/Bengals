package com.bengals.redistricting_project.Ensembles.EnsembleCurves;

import com.bengals.redistricting_project.Ensembles.EnsembleCurves.Collections.EnsembleCurve;
import org.springframework.stereotype.Service;

@Service
public class EnsembleCurveService {
    private final EnsembleCurveRepository ensembleCurveRepository;

    public EnsembleCurveService(EnsembleCurveRepository ensembleCurveRepository) {
        this.ensembleCurveRepository = ensembleCurveRepository;
    }

    public EnsembleCurve findEnsembleCurve(String state) {
        for(EnsembleCurve ensembleCurve : ensembleCurveRepository.findAll()){
            if(ensembleCurve.getState().startsWith(state)){
                return ensembleCurve;
            }
        }
        return null;
    }
}
