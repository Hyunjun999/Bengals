package com.bengals.redistricting_project.StateDistricts.MMD.Repository;

import com.bengals.redistricting_project.StateDistricts.MMD.Collections.MSStateDistrictMMD;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSStateDistrictRepositoryMMD extends MongoRepository<MSStateDistrictMMD, ObjectId> {
}
