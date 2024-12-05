package com.bengals.redistricting_project.States;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends MongoRepository<State, String> {
    State findByState(String state);
}
