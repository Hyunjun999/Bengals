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
public class RacialOpportunityDto {
    private OpportunityDistrictsDto opportunityDistrictsBar;
    private OpportunityRepresentativesDto opportunityRepresentativesBar;

    public static RacialOpportunityDto toRacialOpportunityDTO(Ensemble ensemble, String districtType) {
        RacialOpportunityDto.RacialOpportunityDtoBuilder racialOpportunityDTOBuilder = RacialOpportunityDto.builder();
        Racial racial = districtType.equalsIgnoreCase("smd")
                ? ensemble.getSmd().getRacial()
                : ensemble.getMmd().getRacial();

//        OpDistricts opDistricts = null;
//        if (districtType.equalsIgnoreCase("smd")) {
//            opDistricts = ensemble.getSmd().getRacial().getOpDistricts();
//        } else {
//            opDistricts = ensemble.getMmd().getRacial().getOpDistricts();
//        }

//        OpDistrictsDTO.OpDistrictsDTOBuilder opDistrictsDTOBuilder = OpDistrictsDTO.builder();
//        opDistrictsDTOBuilder.blk(opPerRace("blk", opDistricts));
//        opDistrictsDTOBuilder.asn(opPerRace("asn", opDistricts));
//        opDistrictsDTOBuilder.hsp(opPerRace("hsp", opDistricts));
//        opDistrictsDTOBuilder.nonWht(opPerRace("non_wht", opDistricts));

        OpportunityDistrictsDto opportunityDistrictsDTO = OpportunityDistrictsDto.builder()
                .black(getOpDistrictsDto(racial.getOpportunityDistricts().getBlack()))
                .asian(getOpDistrictsDto(racial.getOpportunityDistricts().getAsian()))
                .hispanic(getOpDistrictsDto(racial.getOpportunityDistricts().getHispanic()))
                .nonWhite(getOpDistrictsDto(racial.getOpportunityDistricts().getNonWhite()))
                .build();

        OpportunityRepresentativesDto opportunityRepresentativesDTO = OpportunityRepresentativesDto.builder()
                .black(getOpRepresentativesDto(racial.getOpportunityRepresentatives().getBlack()))
                .asian(getOpRepresentativesDto(racial.getOpportunityRepresentatives().getAsian()))
                .hispanic(getOpRepresentativesDto(racial.getOpportunityRepresentatives().getHispanic()))
                .nonWhite(getOpRepresentativesDto(racial.getOpportunityRepresentatives().getNonWhite()))
                .build();

//        OpRepresentatives opRepresentatives = null;
//        if (districtType.equalsIgnoreCase("smd")) {
//            opRepresentatives = ensemble.getSmd().getRacial().getOpRepresentatives();
//        } else {
//            opRepresentatives = ensemble.getMmd().getRacial().getOpRepresentatives();
//        }
//        OpRepresentativesDTO.OpRepresentativesDTOBuilder opRepresentativesDTOBuilder = OpRepresentativesDTO.builder();
//        opRepresentativesDTOBuilder.blk(repPerRace("blk", opRepresentatives));
//        opRepresentativesDTOBuilder.asn(repPerRace("asn", opRepresentatives));
//        opRepresentativesDTOBuilder.hsp(repPerRace("hsp", opRepresentatives));
//        opRepresentativesDTOBuilder.nonWht(repPerRace("non_wht", opRepresentatives));

        return racialOpportunityDTOBuilder
                .opportunityDistrictsBar(opportunityDistrictsDTO)
                .opportunityRepresentativesBar(opportunityRepresentativesDTO)
                .build();
    }

    public static List<OpportunityDistrictsElementDto> getOpDistrictsDto(List<OpportunityDistrictsElement> raceSelected) {
        Map<Integer, Integer> map = new HashMap<>();
        for (OpportunityDistrictsElement element : raceSelected) {
            map.merge(element.getNumOpportunityDistricts(), 1, Integer::sum);
        }
        List<OpportunityDistrictsElementDto> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result.add(new OpportunityDistrictsElementDto(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public static List<OpportunityRepresentativesElementDto> getOpRepresentativesDto(List<OpportunityRepresentativesElement> raceSelected) {
        Map<Integer, Integer> map = new HashMap<>();
        for (OpportunityRepresentativesElement element : raceSelected) {
            map.merge(element.getNumOpportunityRepresentatives(), 1, Integer::sum);
        }
        List<OpportunityRepresentativesElementDto> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result.add(new OpportunityRepresentativesElementDto(entry.getKey(), entry.getValue()));
        }
        return result;
    }
}
//    public static List<OpRepresentativesElementDTO> repPerRace(String race, OpRepresentatives opRepresentatives) {
//        List<OpRepresentativesElement> raceSelected = null;
//        Map<Integer, Integer> map = new HashMap<>();
//        if (race.equalsIgnoreCase("blk")) {
//            raceSelected = opRepresentatives.getBlk();
//        } else if (race.equalsIgnoreCase("asn")) {
//            raceSelected = opRepresentatives.getAsn();
//        } else if (race.equalsIgnoreCase("hsp")) {
//            raceSelected = opRepresentatives.getHsp();
//        } else if (race.equalsIgnoreCase("non_wht")) {
//            raceSelected = opRepresentatives.getNonWht();
//        }
//        for (int i = 0; i < opRepresentatives.getBlk().size(); i++) {
//            if (!map.containsKey(raceSelected.get(i).getNumOpRepresentatives())) {
//                map.put(raceSelected.get(i).getNumOpRepresentatives(), 1);
//            } else {
//                map.put(raceSelected.get(i).getNumOpRepresentatives(), map.get(raceSelected.get(i).getNumOpRepresentatives()) + 1);
//            }
//        }
//        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
//        List<OpRepresentativesElementDTO> arr = new ArrayList<>();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, Integer> next = iterator.next();
//            OpRepresentativesElementDTO elementDTO = new OpRepresentativesElementDTO();
//            elementDTO.setName(next.getKey());
//            elementDTO.setOpRepresentatives(next.getValue());
//            arr.add(elementDTO);
//        }
//        return arr;
//    }
//
//    public static List<OpDistrictsElementDTO> opPerRace(String race, OpDistricts opDistricts) {
//        List<OpDistrictsElement> raceSelected = null;
//        Map<Integer, Integer> map = new HashMap<>();
//        if (race.equalsIgnoreCase("blk")) {
//            raceSelected = opDistricts.getBlk();
//        } else if (race.equalsIgnoreCase("asn")) {
//            raceSelected = opDistricts.getAsn();
//        } else if (race.equalsIgnoreCase("hsp")) {
//            raceSelected = opDistricts.getHsp();
//        } else if (race.equalsIgnoreCase("non_wht")) {
//            raceSelected = opDistricts.getNonWht();
//        }
//        for (int i = 0; i < opDistricts.getBlk().size(); i++) {
//            if (!map.containsKey(raceSelected.get(i).getNumOpDistricts())) {
//                map.put(raceSelected.get(i).getNumOpDistricts(), 1);
//            } else {
//                map.put(raceSelected.get(i).getNumOpDistricts(), map.get(raceSelected.get(i).getNumOpDistricts()) + 1);
//            }
//        }
//        Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
//        List<OpDistrictsElementDTO> arr = new ArrayList<>();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, Integer> next = iterator.next();
//            OpDistrictsElementDTO elementDTO = new OpDistrictsElementDTO();
//            elementDTO.setName(next.getKey());
//            elementDTO.setNumOpDistricts(next.getValue());
//            arr.add(elementDTO);
//        }
//        return arr;
//    }
