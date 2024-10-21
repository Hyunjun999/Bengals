package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.EnsembleBWs.Collections.EnsembleBW;
import com.bengals.redistricting_project.Ensembles.EnsembleBWs.EnsembleBWService;
import com.bengals.redistricting_project.Ensembles.EnsembleCurves.Collections.EnsembleCurve;
import com.bengals.redistricting_project.Ensembles.EnsembleCurves.EnsembleCurveRepository;
import com.bengals.redistricting_project.Ensembles.EnsembleCurves.EnsembleCurveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnsembleController {

    private final EnsembleBWService ensembleBWService;
    private final EnsembleCurveService ensembleCurveService;

    public EnsembleController(EnsembleBWService ensembleBWService, EnsembleCurveService ensembleCurveService) {
        this.ensembleBWService = ensembleBWService;
        this.ensembleCurveService = ensembleCurveService;
    }

    @GetMapping("/{state}/ensemble/bw")
    public EnsembleBW getEnsembleBW(@PathVariable String state) {
        return ensembleBWService.findEnsembleBW(state);
    }

    @GetMapping("/{state}/ensemble/curve")
    public EnsembleCurve getEnsembleCurve(@PathVariable String state) {
        return ensembleCurveService.findEnsembleCurve(state);
    }
}
