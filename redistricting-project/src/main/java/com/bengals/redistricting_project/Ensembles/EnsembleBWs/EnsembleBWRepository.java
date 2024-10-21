package com.bengals.redistricting_project.Ensembles.EnsembleBWs;

import com.bengals.redistricting_project.Ensembles.EnsembleBWs.Collections.EnsembleBW;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnsembleBWRepository extends MongoRepository<EnsembleBW, ObjectId> {
}
