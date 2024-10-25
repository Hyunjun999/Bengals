package com.bengals.redistricting_project.State;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "State")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class State {
    private String state;
    private int total_pop;
    private int vote_pop;
    private int total_seats;
    private PartySplit party_splits;
}
