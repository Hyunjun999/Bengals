package com.bengals.redistricting_project.DistrictPlanStat;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateDistrictPlanStatService {
    private final StateDistrictPlanStatRepository stateDistrictPlanStatRepository;

    public StateDistrictPlanStatService(StateDistrictPlanStatRepository stateDistrictPlanStatRepository) {
        this.stateDistrictPlanStatRepository = stateDistrictPlanStatRepository;
    }

    public List<StateDistrictPlanStat> findAllMMDPlanStat(String state, String disType) {
        return stateDistrictPlanStatRepository.findByDistrictIdStartingWithAndType(state, disType);
    }

    public List<StateDistrictPlanStat> findAllSMDPlanStat(String state) {
        return stateDistrictPlanStatRepository.findByDistrictIdStartingWithAndEndingWithNumber(state);

    }
}