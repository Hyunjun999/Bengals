package com.bengals.redistricting_project.State;

import org.springframework.stereotype.Service;

@Service
public class StateService {

    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State findStateInfo(String state) {
        return stateRepository.findByState(state);
    }
}
