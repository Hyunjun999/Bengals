package com.bengals.redistricting_project.StateDistricts.MMD;

import com.bengals.redistricting_project.StateDistricts.MMD.Collections.ALStateDistrictMMD;
import com.bengals.redistricting_project.StateDistricts.MMD.Collections.MSStateDistrictMMD;
import com.bengals.redistricting_project.StateDistricts.MMD.Collections.PAStateDistrictMMD;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrictDto;
import com.bengals.redistricting_project.StateDistricts.MMD.Repository.ALStateDistrictRepositoryMMD;
import com.bengals.redistricting_project.StateDistricts.MMD.Repository.MSStateDistrictRepositoryMMD;
import com.bengals.redistricting_project.StateDistricts.MMD.Repository.PAStateDistrictRepositoryMMD;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateDistrictServicsMMD {

    private final ALStateDistrictRepositoryMMD alStateDistrictRepositoryMMD;
    private final MSStateDistrictRepositoryMMD msStateDistrictRepositoryMMD;
    private final PAStateDistrictRepositoryMMD paStateDistrictRepositoryMMD;

    public StateDistrictServicsMMD(ALStateDistrictRepositoryMMD alStateDistrictRepositoryMMD,
                                   MSStateDistrictRepositoryMMD msStateDistrictRepositoryMMD,
                                   PAStateDistrictRepositoryMMD paStateDistrictRepositoryMMD
    ) {
        this.alStateDistrictRepositoryMMD = alStateDistrictRepositoryMMD;
        this.msStateDistrictRepositoryMMD = msStateDistrictRepositoryMMD;
        this.paStateDistrictRepositoryMMD = paStateDistrictRepositoryMMD;
    }

    public List<StateDistrictDto> findStateDistrictsMMD(String state) {
        List<StateDistrictDto> list = new ArrayList<>();
        if (state.equals("AL")) {
            for (ALStateDistrictMMD alStateDistrictMMD : alStateDistrictRepositoryMMD.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(alStateDistrictMMD.getId())
                        .type(alStateDistrictMMD.getType())
                        .features(alStateDistrictMMD.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        } else if (state.equals("MS")) {
            for (MSStateDistrictMMD msStateDistrictMMD : msStateDistrictRepositoryMMD.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(msStateDistrictMMD.getId())
                        .type(msStateDistrictMMD.getType())
                        .features(msStateDistrictMMD.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        } else if (state.equals("PA")) {
            for (PAStateDistrictMMD paStateDistrictMMD : paStateDistrictRepositoryMMD.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(paStateDistrictMMD.getId())
                        .type(paStateDistrictMMD.getType())
                        .features(paStateDistrictMMD.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        }
        return list;
    }
}
