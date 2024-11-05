package com.bengals.redistricting_project.StateDistricts.Collections;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class StateProperties {
    private String district_id;
    private int num_dist;
    private int total_pop;
    private int vote_pop;
    private int total_asn;
    private int total_blk;
    private int total_hsp;
    private int total_wht;
    private int vote_dem;
    private int vote_rep;
    private int total_vote;
    private int rep_wins;
    private int dem_wins;
    private double op_threshold;
    private String win_pty;
    private String centroid;
}
