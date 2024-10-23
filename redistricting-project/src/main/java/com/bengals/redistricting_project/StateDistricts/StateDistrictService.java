package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.ALStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.MSStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.PAStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrictDto;
import com.bengals.redistricting_project.StateDistricts.Repository.ALStateDistrictRepository;
import com.bengals.redistricting_project.StateDistricts.Repository.MSStateDistrictRepository;
import com.bengals.redistricting_project.StateDistricts.Repository.PAStateDistrictRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateDistrictService {

    private final ALStateDistrictRepository alStateDistrictRepository;
    private final MSStateDistrictRepository msStateDistrictRepository;
    private final PAStateDistrictRepository paStateDistrictRepository;

    public StateDistrictService(ALStateDistrictRepository alStateDistrictRepository,
                                MSStateDistrictRepository msStateDistrictRepository,
                                PAStateDistrictRepository paStateDistrictRepository
    ) {
        this.alStateDistrictRepository = alStateDistrictRepository;
        this.msStateDistrictRepository = msStateDistrictRepository;
        this.paStateDistrictRepository = paStateDistrictRepository;
    }

    public List<StateDistrictDto> findStateDistricts(String state) {
        List<StateDistrictDto> list = new ArrayList<>();
        int plan_id = 0;
        if (state.equals("AL")) {
            for (ALStateDistrict alStateDistrict : alStateDistrictRepository.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();

                stateDistrictDto = StateDistrictDto.builder()
                        .id(alStateDistrict.getId())
                        .type(alStateDistrict.getType())
                        .plan_id(plan_id++)
                        .features(alStateDistrict.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        } else if (state.equals("MS")) {
            for (MSStateDistrict msStateDistrict : msStateDistrictRepository.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(msStateDistrict.getId())
                        .type(msStateDistrict.getType())
                        .plan_id(plan_id++)
                        .features(msStateDistrict.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        } else if (state.equals("PA")) {
            for (PAStateDistrict paStateDistrict : paStateDistrictRepository.findAll()) {
                StateDistrictDto stateDistrictDto = new StateDistrictDto();
                stateDistrictDto = StateDistrictDto.builder()
                        .id(paStateDistrict.getId())
                        .plan_id(plan_id++)
                        .type(paStateDistrict.getType())
                        .features(paStateDistrict.getFeatures())
                        .build();
                list.add(stateDistrictDto);
            }
        }
        return list;
    }

//    public StateDistrictDto findStateDistricts(String state) {
//        StateDistrictDto stateDistrictDto = new StateDistrictDto();
//        if (state.equals("AL")) {
//            ALStateDistrict alStateDistrict = alStateDistrictRepository.findAll().get(0);
//            stateDistrictDto = StateDistrictDto.builder()
//                    .id(alStateDistrict.getId())
//                    .type(alStateDistrict.getType())
//                    .features(alStateDistrict.getFeatures())
//                    .build();
//
//        } else if (state.equals("MS")) {
//            MSStateDistrict msStateDistrict = msStateDistrictRepository.findAll().get(0);
//            stateDistrictDto = StateDistrictDto.builder()
//                    .id(msStateDistrict.getId())
//                    .type(msStateDistrict.getType())
//                    .features(msStateDistrict.getFeatures())
//                    .build();
//        } else if (state.equals("PA")) {
//            PAStateDistrict paStateDistrict = paStateDistrictRepository.findAll().get(0);
//            stateDistrictDto = StateDistrictDto.builder()
//                    .id(paStateDistrict.getId())
//                    .type(paStateDistrict.getType())
//                    .features(paStateDistrict.getFeatures())
//                    .build();
//        }
//        return stateDistrictDto;
//    }
}
