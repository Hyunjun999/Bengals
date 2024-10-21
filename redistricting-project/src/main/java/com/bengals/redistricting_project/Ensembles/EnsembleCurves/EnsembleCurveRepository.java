package com.bengals.redistricting_project.Ensembles.EnsembleCurves;

import com.bengals.redistricting_project.Ensembles.EnsembleCurves.Collections.EnsembleCurve;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnsembleCurveRepository extends MongoRepository<EnsembleCurve, ObjectId> {
}
