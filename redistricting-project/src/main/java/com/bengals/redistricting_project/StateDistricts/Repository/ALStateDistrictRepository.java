package com.bengals.redistricting_project.StateDistricts.Repository;

import com.bengals.redistricting_project.StateDistricts.Collections.ALStateDistrict;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ALStateDistrictRepository extends MongoRepository<ALStateDistrict, ObjectId> {
}
