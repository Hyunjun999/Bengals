package com.bengals.redistricting_project.Ensembles;

import com.bengals.redistricting_project.Ensembles.Collections.*;
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

    public EnsembleDto findEnsemble(String state) {
        EnsembleDto ensembleDto = new EnsembleDto();
        if (state.equals("AL")) {
            ALEnsemble alEnsemble = alEnsembleRepository.findAll().get(0);
            ensembleDto = EnsembleDto.builder()
                    .box_whisker(alEnsemble.getNon_white())
                    .minority_curve(alEnsemble.getSeatsVotes())
                    .build();

        } else if (state.equals("PA")) {
            paEnsembleRepository.findAll();
            PAEnsemble paEnsemble = paEnsembleRepository.findAll().get(0);
            ensembleDto = EnsembleDto.builder()
                    .box_whisker(paEnsemble.getNon_white())
                    .minority_curve(paEnsemble.getSeatsVotes())
                    .build();
            System.out.println(ensembleDto.getMinority_curve());
        } else if (state.equals("MS")) {
            msEnsembleRepository.findAll();
            MSEnsemble msEnsemble = msEnsembleRepository.findAll().get(0);
            ensembleDto = EnsembleDto.builder()
                    .box_whisker(msEnsemble.getNon_white())
                    .minority_curve(msEnsemble.getSeatsVotes())
                    .build();
        }
        return ensembleDto;
    }
}
