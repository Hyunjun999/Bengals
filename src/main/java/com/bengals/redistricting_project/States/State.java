package com.bengals.redistricting_project.States;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "StateInfo")
@Data
public class State {
    private String state;
    private int totalPopulation;
    private RacialPopulation racialPopulation;
    private int totalSeats;
    private double republican;
    private double democratic;
}
