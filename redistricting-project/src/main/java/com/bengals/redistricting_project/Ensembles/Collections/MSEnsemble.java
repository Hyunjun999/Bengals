package com.bengals.redistricting_project.Ensembles.Collections;

import com.bengals.redistricting_project.StateDistricts.Collections.StateCrs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "MSEnsemble")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MSEnsemble {
    @Id
    private ObjectId id;
    private List<NonWhite> non_white;
    private SeatsVotes seatsVotes;
}
