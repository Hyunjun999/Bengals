package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnsembleController {

    private final EnsembleService ensembleBWService;


    public EnsembleController(EnsembleService ensembleService) {
        this.ensembleBWService = ensembleService;
    }

    @GetMapping("/{state}/ensemble")
    public Ensemble getEnsemble(@PathVariable String state) {
        return ensembleBWService.findEnsemble(state);
    }

}
