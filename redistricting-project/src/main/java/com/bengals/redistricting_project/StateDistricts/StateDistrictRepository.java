package com.bengals.redistricting_project.StateDistricts;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDistrictRepository extends MongoRepository<StateDistrictType, ObjectId> {
    @Query("{ 'state': ?0, 'dis_type': ?1 }")
    StateDistrictType findByStateAndDisType(String state, String disType);
}