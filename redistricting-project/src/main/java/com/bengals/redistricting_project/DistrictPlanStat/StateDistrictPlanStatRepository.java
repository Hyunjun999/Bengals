package com.bengals.redistricting_project.DistrictPlanStat;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateDistrictPlanStatRepository extends MongoRepository<StateDistrictPlanStat, ObjectId> {
    @Query("{ 'district_id': { $regex: '^?0.*?1$', $options: 'i' } }")
    List<StateDistrictPlanStat> findByDistrictIdStartingWithAndType(String state, String disType);

    @Query("{ 'district_id': { $regex: '^?0.*_[0-9]+$', $options: 'i' } }")
    List<StateDistrictPlanStat> findByDistrictIdStartingWithAndEndingWithNumber(String state);

    @Query("{ 'state': ?0, 'dis_type': ?1 }")
    List<StateDistrictPlanStat> findByStateAndDisType(String state, String disType);
}