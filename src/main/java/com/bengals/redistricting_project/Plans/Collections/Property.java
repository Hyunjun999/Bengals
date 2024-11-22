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
    private int total_pop;
    private int vote_pop;
    private int total_wht;
    private int total_asn;
    private int total_blk;
    private int total_hsp;
    private int vote_dem;
    private int vote_rep;
    private String win_pty;
    private String win_pty_votes;
    private double op_threshold;
    private String centroid;

    public String getWin_pty_votes() {
        return win_pty_votes == null ? "" : win_pty_votes;
    }
}
