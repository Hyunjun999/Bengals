package com.bengals.redistricting_project.StateDistricts.SMD;

import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrictDto;
import com.bengals.redistricting_project.StateDistricts.SMD.Collections.ALStateDistrictSMD;
import com.bengals.redistricting_project.StateDistricts.SMD.Collections.MSStateDistrictSMD;
import com.bengals.redistricting_project.StateDistricts.SMD.Collections.PAStateDistrictSMD;
import com.bengals.redistricting_project.StateDistricts.SMD.Repository.ALStateDistrictRepositorySMD;
import com.bengals.redistricting_project.StateDistricts.SMD.Repository.MSStateDistrictRepositorySMD;
import com.bengals.redistricting_project.StateDistricts.SMD.Repository.PAStateDistrictRepositorySMD;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateDistrictServiceSMD {

    private final ALStateDistrictRepositorySMD alStateDistrictRepositorySMD;
    private final MSStateDistrictRepositorySMD msStateDistrictRepositorySMD;
    private final PAStateDistrictRepositorySMD paStateDistrictRepositorySMD;

    public StateDistrictServiceSMD(ALStateDistrictRepositorySMD alStateDistrictRepositorySMD,
                                   MSStateDistrictRepositorySMD msStateDistrictRepositorySMD,
                                   PAStateDistrictRepositorySMD paStateDistrictRepositorySMD
    ) {
        this.alStateDistrictRepositorySMD = alStateDistrictRepositorySMD;
        this.msStateDistrictRepositorySMD = msStateDistrictRepositorySMD;
        this.paStateDistrictRepositorySMD = paStateDistrictRepositorySMD;
    }

    public List<StateDistrictDto> findStateDistrictsSMD(String state) {
        List<StateDistrictDto> list = new ArrayList<>();
        if (state.equals("AL")) {
            for (ALStateDistrictSMD alStateDistrictSMD : alStateDistrictRepositorySMD.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(alStateDistrictSMD.getId())
                        .type(alStateDistrictSMD.getType())
                        .features(alStateDistrictSMD.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        } else if (state.equals("MS")) {
            for (MSStateDistrictSMD msStateDistrictSMD : msStateDistrictRepositorySMD.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(msStateDistrictSMD.getId())
                        .type(msStateDistrictSMD.getType())
                        .features(msStateDistrictSMD.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        } else if (state.equals("PA")) {
            for (PAStateDistrictSMD paStateDistrictSMD : paStateDistrictRepositorySMD.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(paStateDistrictSMD.getId())
                        .type(paStateDistrictSMD.getType())
                        .features(paStateDistrictSMD.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        }
        return list;
    }
}
