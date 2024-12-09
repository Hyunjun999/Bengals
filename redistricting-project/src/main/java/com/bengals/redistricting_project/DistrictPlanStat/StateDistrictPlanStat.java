package com.bengals.redistricting_project.DistrictPlanStat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "StateDistrictPlanStatistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StateDistrictPlanStat {
    private String district_id;
    private String state;
    private String dis_type;
    @JsonProperty("Republicans")
    private int Republicans;
    @JsonProperty("Democrats")
    private int Democrats;
    private int op_districts;
    private int safe_districts;
    private double op_threshold;
}
