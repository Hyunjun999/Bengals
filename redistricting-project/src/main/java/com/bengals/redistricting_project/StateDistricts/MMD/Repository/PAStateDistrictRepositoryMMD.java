package com.bengals.redistricting_project.StateDistricts.MMD.Repository;

import com.bengals.redistricting_project.StateDistricts.MMD.Collections.PAStateDistrictMMD;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PAStateDistrictRepositoryMMD extends MongoRepository<PAStateDistrictMMD, ObjectId> {
}
