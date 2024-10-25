package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "EnsembleSMD")

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EnsembleSMD {
    @Id
    private ObjectId id;
    private List<BW> box_whisker;
    private List<VoteSeats> vote_seats;
}
