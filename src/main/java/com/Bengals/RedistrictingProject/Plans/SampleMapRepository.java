package com.Bengals.RedistrictingProject.Plans;

import com.Bengals.RedistrictingProject.Plans.Collections.SampleMap;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleMapRepository extends MongoRepository<SampleMap, ObjectId> {
    @Query("{ 'state': ?0, 'districtType': ?1 }")
    SampleMap findBy(String state, String districtType);
}
