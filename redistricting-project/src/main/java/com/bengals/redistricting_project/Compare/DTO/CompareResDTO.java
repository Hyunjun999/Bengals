package com.bengals.redistricting_project.Compare.DTO;

import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarDemocrats;
import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarOpDistricts;
import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarOpRep;
import com.bengals.redistricting_project.Compare.DTO.BarComponentsDTO.BarRepublicans;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Compare")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompareResDTO {
    private String state;
    private List<BarRepublicans> republicans_bar;
    private List<BarDemocrats> democrats_bar;
    private List<BarOpDistricts> op_districts_bar;
    private List<BarOpRep> op_representatives_bar;
}
