package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.EnsembleDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnsembleController {
    private final EnsembleService ensembleService;

    public EnsembleController(EnsembleService ensembleService) {
        this.ensembleService = ensembleService;
    }

    @GetMapping("/{state}/ensemble")
    public EnsembleDTO getEnsemble(@PathVariable String state) {
        return ensembleService.findEnsemble(state);
    }
}
