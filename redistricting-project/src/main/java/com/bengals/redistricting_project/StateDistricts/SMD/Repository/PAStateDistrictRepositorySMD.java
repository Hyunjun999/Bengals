package com.bengals.redistricting_project.StateDistricts.SMD.Repository;

import com.bengals.redistricting_project.StateDistricts.SMD.Collections.PAStateDistrictSMD;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PAStateDistrictRepositorySMD extends MongoRepository<PAStateDistrictSMD, ObjectId> {
}
