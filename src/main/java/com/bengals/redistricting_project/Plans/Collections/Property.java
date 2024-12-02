package com.bengals.redistricting_project.Plans.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    private int numDistrict;
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
    public String getWin_pty_votes() {
        return winningPartyVotes == null ? "" : winningPartyVotes;
    }
}
