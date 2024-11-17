package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnsembleSummaryDTO {
    private int num_dist_plan;
    private double avg_min_max_diff;
    private double non_wht;
    private PartySplit avg_party_split;
    private List<PartySplit> seats_votes;
    private double bias;
    private double symmetry;
    private PartySplit responsiveness;
    private PartySplit avg_seat_share;

    public static EnsembleSummaryDTO toEnsembleSummaryDTO(Ensemble ensemble, String districtType) { //Entity -> DTO
        EnsembleSummaryDTOBuilder ensembleSummaryDTOBuilder = EnsembleSummaryDTO.builder();
        Summary summary = null;
        if (districtType.equalsIgnoreCase("smd")) {
            summary = ensemble.getSmd().getSummary();
        } else {
            summary = ensemble.getMmd().getSummary();
            System.out.println(summary);
        }
        return ensembleSummaryDTOBuilder
                .num_dist_plan(summary.getNum_dist_plan())
                .avg_min_max_diff(summary.getAvg_min_max_diff())
                .non_wht(summary.getAvg_num_minor_representatives().getNon_wht())
                .avg_party_split(summary.getAvg_party_split())
                .seats_votes(summary.getSeats_votes())
                .bias(summary.getBias())
                .symmetry(summary.getSymmetry())
                .responsiveness(summary.getResponsiveness())
                .build();
    }
}
