package com.bengals.redistricting_project.Compare;

import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarDemocrats;
import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarOpDistricts;
import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarOpRep;
import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarRepublicans;
import com.bengals.redistricting_project.Compare.DTO.CompareReqDTO;
import com.bengals.redistricting_project.Compare.DTO.CompareResDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompareService {
    private final CompareRepository compareRepository;

    public CompareService(CompareRepository compareRepository) {
        this.compareRepository = compareRepository;
    }

    public CompareResDTO findCompare(String state) {
        CompareReqDTO compareReqDTO = compareRepository.findByState(state);
        CompareResDTO compareResDTO = new CompareResDTO();
        String nameCurrentSMD = "currentSMD";
        String nameAvgMMD = "averageMMD";

        BarRepublicans barRepublicansSMD = new BarRepublicans();
        BarRepublicans barRepublicansMMD = new BarRepublicans();
        barRepublicansSMD.setName(nameCurrentSMD);
        barRepublicansSMD.setRepublicans(compareReqDTO.getCurrent_SMD().getRepublicans());
        barRepublicansMMD.setName(nameAvgMMD);
        barRepublicansMMD.setRepublicans(compareReqDTO.getAvg_MMD().getRepublicans());

        BarDemocrats barDemocratsSMD = new BarDemocrats();
        BarDemocrats barDemocratsMMD = new BarDemocrats();
        barDemocratsSMD.setName(nameCurrentSMD);
        barDemocratsSMD.setDemocrats(compareReqDTO.getCurrent_SMD().getDemocrats());
        barDemocratsMMD.setName(nameAvgMMD);
        barDemocratsMMD.setDemocrats(compareReqDTO.getAvg_MMD().getDemocrats());

        BarOpDistricts barOpDistrictsSMD = new BarOpDistricts();
        BarOpDistricts barOpDistrictsMMD = new BarOpDistricts();
        barOpDistrictsSMD.setName(nameCurrentSMD);
        barOpDistrictsSMD.setOp_districts(compareReqDTO.getCurrent_SMD().getOp_districts());
        barOpDistrictsMMD.setName(nameAvgMMD);
        barOpDistrictsMMD.setOp_districts(compareReqDTO.getAvg_MMD().getOp_districts());

        BarOpRep barOpRepSMD = new BarOpRep();
        BarOpRep barOpRepMMD = new BarOpRep();
        barOpRepSMD.setName(nameCurrentSMD);
        barOpRepSMD.setOp_representatives(compareReqDTO.getCurrent_SMD().getOp_representatives());
        barOpRepMMD.setName(nameAvgMMD);
        barOpRepMMD.setOp_representatives(compareReqDTO.getAvg_MMD().getOp_representatives());

        List<BarRepublicans> barRepublicans = new ArrayList<>();
        List<BarDemocrats> barDemocrats = new ArrayList<>();
        List<BarOpDistricts> barOpDistricts = new ArrayList<>();
        List<BarOpRep> barOpReps = new ArrayList<>();

        barRepublicans.add(barRepublicansSMD);
        barRepublicans.add(barRepublicansMMD);
        barDemocrats.add(barDemocratsSMD);
        barDemocrats.add(barDemocratsMMD);
        barOpDistricts.add(barOpDistrictsSMD);
        barOpDistricts.add(barOpDistrictsMMD);
        barOpReps.add(barOpRepSMD);
        barOpReps.add(barOpRepMMD);

        compareResDTO.setState(state);
        compareResDTO.setRepublicans_bar(barRepublicans);
        compareResDTO.setDemocrats_bar(barDemocrats);
        compareResDTO.setOp_districts_bar(barOpDistricts);
        compareResDTO.setOp_representatives_bar(barOpReps);

        return compareResDTO;
    }
}