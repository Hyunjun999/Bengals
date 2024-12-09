package com.bengals.redistricting_project.State;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends MongoRepository<State, String> {
    public State findByState(String state);
}
