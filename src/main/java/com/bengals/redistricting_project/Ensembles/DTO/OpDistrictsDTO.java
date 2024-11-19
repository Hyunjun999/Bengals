package com.bengals.redistricting_project.Ensembles.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OpDistrictsDTO {
    List<OpDistrictsElementDTO> blk;
    List<OpDistrictsElementDTO> asn;
    List<OpDistrictsElementDTO> hsp;
    List<OpDistrictsElementDTO> non_wht;
}
