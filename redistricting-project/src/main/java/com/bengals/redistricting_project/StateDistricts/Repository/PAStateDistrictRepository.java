package com.bengals.redistricting_project.StateDistricts.Repository;

import com.bengals.redistricting_project.StateDistricts.Collections.PAStateDistrict;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PAStateDistrictRepository extends MongoRepository<PAStateDistrict, ObjectId> {
}
