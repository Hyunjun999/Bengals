package com.bengals.redistricting_project.StateDistricts;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateDistrictRepository extends MongoRepository<StateDistrictReqDTO, ObjectId> {
//    @Query("{ 'state': ?0, 'dis_type': ?1 }")
//    StateDistrictReqDTO findByStateAndDisType(String state, String disType);

    //    @Query("{ 'features.properties.district_id': { $regex: '^?0', $options: 'i' } }")
//    List<StateDistrictReqDTO> findByDistrictIdStartingWith(String prefix);
    @Query("{ 'features.properties.district_id': { $regex: '^?0.*?1$', $options: 'i' } }")
    List<StateDistrictReqDTO> findByDistrictIdStartingWithAndType(String state, String disType);

    @Query("{ 'features.properties.district_id': { $regex: '^?0.*_[0-9]+$', $options: 'i' } }")
    List<StateDistrictReqDTO> findByDistrictIdStartingWithAndEndingWithNumber(String state);

}