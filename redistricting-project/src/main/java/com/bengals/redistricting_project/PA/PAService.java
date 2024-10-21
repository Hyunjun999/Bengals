package com.bengals.redistricting_project.PA;

import com.bengals.redistricting_project.PA.Collection.PADistrict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PAService {

    private final PADistrictRepository paDistrictRepository;

    public PAService(PADistrictRepository paDistrictRepository) {
        this.paDistrictRepository = paDistrictRepository;
    }

    public List<PADistrict> findAllDistricts() {
        return paDistrictRepository.findAll();
    }
}
