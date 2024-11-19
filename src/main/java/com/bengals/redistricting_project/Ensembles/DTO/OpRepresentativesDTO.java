package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.OpRepresentativesElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpRepresentativesDTO {
    private List<OpRepresentativesElementDTO> blk;
    private List<OpRepresentativesElementDTO> asn;
    private List<OpRepresentativesElementDTO> hsp;
    private List<OpRepresentativesElementDTO> non_wht;
}
