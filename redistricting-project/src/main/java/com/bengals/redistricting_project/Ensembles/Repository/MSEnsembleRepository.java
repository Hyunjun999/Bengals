package com.bengals.redistricting_project.Ensembles.Repository;

import com.bengals.redistricting_project.Ensembles.Collections.MSEnsemble;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MSEnsembleRepository extends MongoRepository<MSEnsemble, ObjectId> {
}
