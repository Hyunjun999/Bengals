package com.bengals.redistricting_project.Ensembles.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityRepComponent {
    List<OpportunityRepComponent> blk;
    List<OpportunityRepComponent> asn;
    List<OpportunityRepComponent> hsp;
    List<OpportunityRepComponent> non_wht;
}
