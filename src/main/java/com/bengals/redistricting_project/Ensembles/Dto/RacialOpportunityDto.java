package com.bengals.redistricting_project.Ensembles.Dto;

import com.bengals.redistricting_project.Ensembles.Collections.*;
import lombok.Builder;
import lombok.Data;
import java.util.*;

@Data
@Builder
public class RacialOpportunityDto {
    private OpportunityDistrictsDto opportunityDistrictsBar;
    private OpportunityRepresentativesDto opportunityRepresentativesBar;

    public static RacialOpportunityDto toRacialOpportunityDto(Ensemble ensemble, String districtType) {
        RacialOpportunityDto.RacialOpportunityDtoBuilder racialOpportunityDtoBuilder = RacialOpportunityDto.builder();
        Racial racial = districtType.equals("smd")
                ? ensemble.getSmd().getRacial()
                : ensemble.getMmd().getRacial();

        OpportunityDistrictsDto opportunityDistrictsDto = OpportunityDistrictsDto.builder()
                .black(getOpportunityDistrictsDto(racial.getOpportunityDistricts().getBlack()))
                .asian(getOpportunityDistrictsDto(racial.getOpportunityDistricts().getAsian()))
                .hispanic(getOpportunityDistrictsDto(racial.getOpportunityDistricts().getHispanic()))
                .nonWhite(getOpportunityDistrictsDto(racial.getOpportunityDistricts().getNonWhite()))
                .build();

        OpportunityRepresentativesDto opportunityRepresentativesDto = OpportunityRepresentativesDto.builder()
                .black(getOpportunityRepresentativesDto(racial.getOpportunityRepresentatives().getBlack()))
                .asian(getOpportunityRepresentativesDto(racial.getOpportunityRepresentatives().getAsian()))
                .hispanic(getOpportunityRepresentativesDto(racial.getOpportunityRepresentatives().getHispanic()))
                .nonWhite(getOpportunityRepresentativesDto(racial.getOpportunityRepresentatives().getNonWhite()))
                .build();

        return racialOpportunityDtoBuilder
                .opportunityDistrictsBar(opportunityDistrictsDto)
                .opportunityRepresentativesBar(opportunityRepresentativesDto)
                .build();
    }

    public static List<OpportunityDistrictsElementDto> getOpportunityDistrictsDto(List<OpportunityDistrictsElement> raceSelected) {
        Map<Integer, Integer> map = new HashMap<>();
        List<OpportunityDistrictsElementDto> elementDto = new ArrayList<>();

        for (OpportunityDistrictsElement element : raceSelected) {
            map.merge(element.getNumOpportunityDistricts(), 1, Integer::sum);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            elementDto.add(new OpportunityDistrictsElementDto(entry.getKey(), entry.getValue()));
        }

        return elementDto;
    }

    public static List<OpportunityRepresentativesElementDto> getOpportunityRepresentativesDto(List<OpportunityRepresentativesElement> raceSelected) {
        Map<Integer, Integer> map = new HashMap<>();
        List<OpportunityRepresentativesElementDto> elementDto = new ArrayList<>();

        for (OpportunityRepresentativesElement element : raceSelected) {
            map.merge(element.getNumOpportunityRepresentatives(), 1, Integer::sum);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            elementDto.add(new OpportunityRepresentativesElementDto(entry.getKey(), entry.getValue()));
        }

        return elementDto;
    }
}