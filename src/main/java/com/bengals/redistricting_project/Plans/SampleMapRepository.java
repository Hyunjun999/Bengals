package com.bengals.redistricting_project.Plans;

import com.bengals.redistricting_project.Plans.Collections.SampleMap;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMapRepository extends MongoRepository<SampleMap, ObjectId> {
    @Query("{ 'state': ?0, 'districtType': ?1 }")
    SampleMap findBy(String state, String districtType);
}