package com.bengals.redistricting_project.PA;

import com.bengals.redistricting_project.PA.Collection.PADistrict;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PADistrictRepository extends MongoRepository<PADistrict, ObjectId> {
}
