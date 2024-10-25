package com.bengals.redistricting_project.StateDistricts.SMD.Repository;

import com.bengals.redistricting_project.StateDistricts.SMD.Collections.MSStateDistrictSMD;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSStateDistrictRepositorySMD extends MongoRepository<MSStateDistrictSMD, ObjectId> {
}
