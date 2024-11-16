package com.bengals.redistricting_project.States;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "StateInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class State {
    private String state;
    private int total_pop;
    private RacialPop racial_pop;
    private int total_seats;
    private double republican;
    private double democratic;
}
