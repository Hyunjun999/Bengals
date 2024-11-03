package com.bengals.redistricting_project.Ensembles.Repository;

import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnsembleRepository extends MongoRepository<Ensemble, ObjectId> {
    public Ensemble findByState(String state);
}
