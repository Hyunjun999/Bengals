package com.bengals.redistricting_project.Plans.Collections;

import lombok.Data;

@Data
public class Property {
    private int districtNumber;
    private int totalPopulation;
    private int votePopulation;
    private int totalWhite;
    private int totalAsian;
    private int totalBlack;
    private int totalHispanic;
    private int democraticVotes;
    private int republicanVotes;
    private String winningParty;
    private String winningPartyVotes;
    private double opportunityThreshold;
    private String centroid;
}