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
    private int numDist;
    private int totalPop;
    private int votePop;
    private int total_Wht;
    private int totalAsn;
    private int totalBlk;
    private int totalHsp;
    private int voteDem;
    private int voteRep;
    private String winPty;
    private String winPtyVotes;
    private double opThreshold;
    private String centroid;
    public String getWin_pty_votes() {
        return winPtyVotes == null ? "" : winPtyVotes;
    }
}
