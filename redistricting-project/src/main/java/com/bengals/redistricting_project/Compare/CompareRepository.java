package com.bengals.redistricting_project.Compare;

import com.bengals.redistricting_project.Compare.DTO.CompareReqDTO;
import com.bengals.redistricting_project.Ensembles.Collections.Ensemble;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompareRepository extends MongoRepository<CompareReqDTO, ObjectId> {
    public CompareReqDTO findByState(String state);
}
