package com.bengals.redistricting_project.Plans.Repository;

import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import com.bengals.redistricting_project.Plans.Collections.Plan;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PlanRepository extends MongoRepository<Plan, ObjectId> {
    public Plan findByState(String state);

    @Query("{ 'state': ?0, 'reason': ?1, 'district_type': ?2 }")
    List<Plan> findBy(String state, String reason, String districtType);


}
