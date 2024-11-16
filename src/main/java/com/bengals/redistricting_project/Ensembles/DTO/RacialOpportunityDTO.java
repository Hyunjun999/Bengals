package com.bengals.redistricting_project.Ensembles.DTO;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RacialOpportunityDTO {
    private OpDistrictsDTO op_districts_bar;
    private OpRepresentativesDTO op_representatives_bar;

    public static RacialOpportunityDTO toRacialOpportunityDTO(Ensemble ensemble, String districtType) {
        RacialOpportunityDTO.RacialOpportunityDTOBuilder racialOpportunityDTOBuilder = RacialOpportunityDTO.builder();
        OpDistricts opDistricts = null;
        if (districtType.equalsIgnoreCase("smd")) {
            opDistricts = ensemble.getSmd().getRacial().getOp_districts();
        } else {
            opDistricts = ensemble.getMmd().getRacial().getOp_districts();
        }
        OpDistrictsDTO.OpDistrictsDTOBuilder opDistrictsDTOBuilder = OpDistrictsDTO.builder();
        opDistrictsDTOBuilder.blk(opPerRace("blk", opDistricts));
        opDistrictsDTOBuilder.hsp(opPerRace("hsp", opDistricts));
        opDistrictsDTOBuilder.non_wht(opPerRace("non_wht", opDistricts));

        OpRepresentatives opRepresentatives = null;
        if (districtType.equalsIgnoreCase("smd")) {
            opRepresentatives = ensemble.getSmd().getRacial().getOp_representatives();
        } else {
            opRepresentatives = ensemble.getMmd().getRacial().getOp_representatives();
        }
        OpRepresentativesDTO.OpRepresentativesDTOBuilder opRepresentativesDTOBuilder = OpRepresentativesDTO.builder();
        opRepresentativesDTOBuilder.blk(repPerRace("blk", opRepresentatives));
        opRepresentativesDTOBuilder.hsp(repPerRace("hsp", opRepresentatives));
        opRepresentativesDTOBuilder.non_wht(repPerRace("non_wht", opRepresentatives));

        return racialOpportunityDTOBuilder
                .op_districts_bar(opDistrictsDTOBuilder.build())
                .op_representatives_bar(opRepresentativesDTOBuilder.build())
                .build();
    }

    public static List<OpRepresentativesElementDTO> repPerRace(String race, OpRepresentatives opRepresentatives) {
        List<OpRepresentativesElement> raceSelected = null;
        Map<Integer, Integer> map = new HashMap<>();
        if (race.equalsIgnoreCase("blk")) {
            raceSelected = opRepresentatives.getBlk();
        } else if (race.equalsIgnoreCase("hsp")) {
            raceSelected = opRepresentatives.getHsp();
        } else if (race.equalsIgnoreCase("non_wht")) {
            raceSelected = opRepresentatives.getNon_wht();
        }
        for (int i = 0; i < opRepresentatives.getBlk().size(); i++) {
            if (!map.containsKey(raceSelected.get(i).getNum_op_representatives())) {
                map.put(raceSelected.get(i).getNum_op_representatives(), 1);
            } else {
                map.put(raceSelected.get(i).getNum_op_representatives(), map.get(raceSelected.get(i).getNum_op_representatives()) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        List<OpRepresentativesElementDTO> arr = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            OpRepresentativesElementDTO elementDTO = new OpRepresentativesElementDTO();
            elementDTO.setName(next.getKey());
            elementDTO.setOp_representatives(next.getValue());
            arr.add(elementDTO);
        }
        return arr;
    }

    public static List<OpDistrictsElementDTO> opPerRace(String race, OpDistricts opDistricts) {
        List<OpDistrictsElement> raceSelected = null;
        Map<Integer, Integer> map = new HashMap<>();
        if (race.equalsIgnoreCase("blk")) {
            raceSelected = opDistricts.getBlk();
        } else if (race.equalsIgnoreCase("hsp")) {
            raceSelected = opDistricts.getHsp();
        } else if (race.equalsIgnoreCase("non_wht")) {
            raceSelected = opDistricts.getNon_wht();
        }
        for (int i = 0; i < opDistricts.getBlk().size(); i++) {
            if (!map.containsKey(raceSelected.get(i).getNum_op_districts())) {
                map.put(raceSelected.get(i).getNum_op_districts(), 1);
            } else {
                map.put(raceSelected.get(i).getNum_op_districts(), map.get(raceSelected.get(i).getNum_op_districts()) + 1);
            }
        }
        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
        List<OpDistrictsElementDTO> arr = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Integer> next = iterator.next();
            OpDistrictsElementDTO elementDTO = new OpDistrictsElementDTO();
            elementDTO.setName(next.getKey());
            elementDTO.setNum_op_districts(next.getValue());
            arr.add(elementDTO);
        }
        return arr;
    }
}
