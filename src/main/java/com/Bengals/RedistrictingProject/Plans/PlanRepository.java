package com.Bengals.RedistrictingProject.Plans;

import com.Bengals.RedistrictingProject.Plans.Collections.Plan;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends MongoRepository<Plan, ObjectId> {
    @Query("{ 'state': ?0, 'reason': ?1, 'districtType': ?2 }")
    Plan findBy(String state, String reason, String districtType);
}