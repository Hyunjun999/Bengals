package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.EnsembleDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnsembleController {
    private final EnsembleService ensembleService;

    public EnsembleController(EnsembleService ensembleService) {
        this.ensembleService = ensembleService;
    }

    @GetMapping("/{state}/ensemble/smd")
    public EnsembleDto getEnsembleSMD(@PathVariable String state) {
        return ensembleService.findEnsembleSMD(state);
    }
    @GetMapping("/{state}/ensemble/mmd")
    public EnsembleDto getEnsembleMMD(@PathVariable String state) {
        return ensembleService.findEnsembleMMD(state);
    }
}
