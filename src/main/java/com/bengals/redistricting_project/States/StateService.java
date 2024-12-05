package com.bengals.redistricting_project.States;

import org.springframework.stereotype.Service;

@Service
public class StateService {
    private final StateRepository stateRepository;

    public StateService(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    public State getStateInfo(String state) {
        return stateRepository.findByState(state);
    }
}