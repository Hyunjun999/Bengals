package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.ALStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.MSStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.PAStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import com.bengals.redistricting_project.StateDistricts.Repository.ALStateDistrictRepository;
import com.bengals.redistricting_project.StateDistricts.Repository.MSStateDistrictRepository;
import com.bengals.redistricting_project.StateDistricts.Repository.PAStateDistrictRepository;
import org.springframework.stereotype.Service;

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

    public StateDistrict findStateDistricts(String state) {
        StateDistrict stateDistrict = new StateDistrict();
        if (state.equals("AL")) {
            ALStateDistrict alStateDistrict = alStateDistrictRepository.findAll().get(0);
            stateDistrict = StateDistrict.builder()
                    .id(alStateDistrict.getId())
                    .type(alStateDistrict.getType())
                    .crs(alStateDistrict.getCrs())
                    .features(alStateDistrict.getFeatures())
                    .build();

        } else if (state.equals("MS")) {
            MSStateDistrict msStateDistrict = msStateDistrictRepository.findAll().get(0);
            stateDistrict = StateDistrict.builder()
                    .id(msStateDistrict.getId())
                    .type(msStateDistrict.getType())
                    .crs(msStateDistrict.getCrs())
                    .features(msStateDistrict.getFeatures())
                    .build();
        } else if (state.equals("PA")) {
            PAStateDistrict paStateDistrict = paStateDistrictRepository.findAll().get(0);
            stateDistrict = StateDistrict.builder()
                    .id(paStateDistrict.getId())
                    .type(paStateDistrict.getType())
                    .crs(paStateDistrict.getCrs())
                    .features(paStateDistrict.getFeatures())
                    .build();
        }
        return stateDistrict;
    }

}
