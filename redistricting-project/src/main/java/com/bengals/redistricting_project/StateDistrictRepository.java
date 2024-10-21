package com.bengals.redistricting_project;

import com.bengals.redistricting_project.Collection.StateDistrict;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateDistrictRepository extends MongoRepository<StateDistrict, ObjectId> {
}
