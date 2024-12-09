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

        Map<String, List<OpportunityDistrictsElementDto>> districtsData = new HashMap<>();
        Map<String, List<OpportunityRepresentativesElementDto>> representativesData = new HashMap<>();

        List<String> races = Arrays.asList("black", "asian", "hispanic", "nonWhite");

        for (String race : races) {
            List<OpportunityDistrictsElementDto> districtsList = getOpportunityDistrictsDto(getDistrictsByRace(racial, race));
            List<OpportunityRepresentativesElementDto> representativesList = getOpportunityRepresentativesDto(getRepresentativesByRace(racial, race));

            int maxKey = Math.max(
                    districtsList.stream().map(OpportunityDistrictsElementDto::getName).max(Integer::compareTo).orElse(0),
                    representativesList.stream().map(OpportunityRepresentativesElementDto::getName).max(Integer::compareTo).orElse(0)
            );

            districtsData.put(race, normalizeList(districtsList, maxKey));
            representativesData.put(race, normalizeList(representativesList, maxKey));
        }

        OpportunityDistrictsDto opportunityDistrictsDto = OpportunityDistrictsDto.builder()
                .black(districtsData.get("black"))
                .asian(districtsData.get("asian"))
                .hispanic(districtsData.get("hispanic"))
                .nonWhite(districtsData.get("nonWhite"))
                .build();

        OpportunityRepresentativesDto opportunityRepresentativesDto = OpportunityRepresentativesDto.builder()
                .black(representativesData.get("black"))
                .asian(representativesData.get("asian"))
                .hispanic(representativesData.get("hispanic"))
                .nonWhite(representativesData.get("nonWhite"))
                .build();

        return racialOpportunityDtoBuilder
                .opportunityDistrictsBar(opportunityDistrictsDto)
                .opportunityRepresentativesBar(opportunityRepresentativesDto)
                .build();
    }

    private static List<OpportunityDistrictsElement> getDistrictsByRace(Racial racial, String race) {
        switch (race) {
            case "black":
                return racial.getOpportunityDistricts().getBlack();
            case "asian":
                return racial.getOpportunityDistricts().getAsian();
            case "hispanic":
                return racial.getOpportunityDistricts().getHispanic();
            case "nonWhite":
                return racial.getOpportunityDistricts().getNonWhite();
            default:
                return Collections.emptyList();
        }
    }

    private static List<OpportunityRepresentativesElement> getRepresentativesByRace(Racial racial, String race) {
        switch (race) {
            case "black":
                return racial.getOpportunityRepresentatives().getBlack();
            case "asian":
                return racial.getOpportunityRepresentatives().getAsian();
            case "hispanic":
                return racial.getOpportunityRepresentatives().getHispanic();
            case "nonWhite":
                return racial.getOpportunityRepresentatives().getNonWhite();
            default:
                return Collections.emptyList();
        }
    }

    private static <T> List<T> normalizeList(List<T> list, int max) {
        Map<Integer, Integer> map = new HashMap<>();
        List<T> result = new ArrayList<>();

        for (T item : list) {
            if (item instanceof OpportunityDistrictsElementDto) {
                OpportunityDistrictsElementDto dto = (OpportunityDistrictsElementDto) item;
                map.put(dto.getName(), dto.getNumOpDistricts());
            } else if (item instanceof OpportunityRepresentativesElementDto) {
                OpportunityRepresentativesElementDto dto = (OpportunityRepresentativesElementDto) item;
                map.put(dto.getName(), dto.getNumOpRepresentatives());
            }
        }

        for (int i = 0; i <= max; i++) {
            if (list.get(0) instanceof OpportunityDistrictsElementDto) {
                result.add((T) new OpportunityDistrictsElementDto(i, map.getOrDefault(i, 0)));
            } else if (list.get(0) instanceof OpportunityRepresentativesElementDto) {
                result.add((T) new OpportunityRepresentativesElementDto(i, map.getOrDefault(i, 0)));
            }
        }

        return result;
    }

    public static List<OpportunityDistrictsElementDto> getOpportunityDistrictsDto(List<OpportunityDistrictsElement> raceSelected) {
        Map<Integer, Integer> map = new HashMap<>();
        List<OpportunityDistrictsElementDto> elementDto = new ArrayList<>();

        for (OpportunityDistrictsElement element : raceSelected) {
            map.merge(element.getNumOpDistricts(), 1, Integer::sum);
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
            map.merge(element.getNumOpRepresentatives(), 1, Integer::sum);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            elementDto.add(new OpportunityRepresentativesElementDto(entry.getKey(), entry.getValue()));
        }

        return elementDto;
    }
}
