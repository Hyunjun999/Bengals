package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.ALStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.MSStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.PAStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import org.springframework.stereotype.Service;

@Service
public class StateDistrictService {

    private final StateDistrictRepository stateDistrictRepository;
    private final ALStateDistrictRepository alStateDistrictRepository;
    private final MSStateDistrictRepository msStateDistrictRepository;
    private final PAStateDistrictRepository paStateDistrictRepository;

    public StateDistrictService(StateDistrictRepository stateDistrictRepository,
                                ALStateDistrictRepository alStateDistrictRepository,
                                MSStateDistrictRepository msStateDistrictRepository,
                                PAStateDistrictRepository paStateDistrictRepository
    ) {
        this.stateDistrictRepository = stateDistrictRepository;
        this.alStateDistrictRepository = alStateDistrictRepository;
        this.msStateDistrictRepository = msStateDistrictRepository;
        this.paStateDistrictRepository = paStateDistrictRepository;
    }

    public StateDistrict findStateDistricts(String state) {
        StateDistrict stateDistrict = new StateDistrict();
        if (state.equals("AL")) {
            ALStateDistrict alStateDistrict = alStateDistrictRepository.findAll().get(0);
            stateDistrict = StateDistrict.builder()
                    .id(alStateDistrict.getId())
                    .type(alStateDistrict.getType())
                    .name(alStateDistrict.getName())
                    .crs(alStateDistrict.getCrs())
                    .features(alStateDistrict.getFeatures())
                    .build();

        }
        else if (state.equals("MS")) {
            MSStateDistrict msStateDistrict = msStateDistrictRepository.findAll().get(0);
            stateDistrict = StateDistrict.builder()
                    .id(msStateDistrict.getId())
                    .type(msStateDistrict.getType())
                    .name(msStateDistrict.getName())
                    .crs(msStateDistrict.getCrs())
                    .features(msStateDistrict.getFeatures())
                    .build();
        }
        else if(state.equals("PA")){
            PAStateDistrict paStateDistrict = paStateDistrictRepository.findAll().get(0);
            stateDistrict = StateDistrict.builder()
                    .id(paStateDistrict.getId())
                    .type(paStateDistrict.getType())
                    .name(paStateDistrict.getName())
                    .crs(paStateDistrict.getCrs())
                    .features(paStateDistrict.getFeatures())
                    .build();
        }
        return stateDistrict;
    }

//    public List<StateDistrict> findAllDistricts() {
//        return stateDistrictRepository.findAll();
//    }
}
