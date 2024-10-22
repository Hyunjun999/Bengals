package com.bengals.redistricting_project.StateDistricts;

import com.bengals.redistricting_project.StateDistricts.Collections.ALStateDistrict;
import com.bengals.redistricting_project.StateDistricts.Collections.StateDistrict;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ALStateDistrictRepository extends MongoRepository<ALStateDistrict, ObjectId> {
}
