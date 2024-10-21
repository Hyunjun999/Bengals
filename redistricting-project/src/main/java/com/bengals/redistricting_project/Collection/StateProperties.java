package com.bengals.redistricting_project.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StateProperties {
    private int total_pop;
    private int vote_pop;
    private int total_asn;
    private int total_blk;
    private int total_hsp;
    private int total_wht;
    private int vote_dem;
    private int vote_rep;
    private int total_vote;
    private String win_cand;
    private String win_pty;
    private String centroid;
}
