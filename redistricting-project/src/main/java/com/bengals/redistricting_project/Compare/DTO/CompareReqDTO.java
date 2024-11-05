package com.bengals.redistricting_project.Compare.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Compare")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompareReqDTO {
    private String state;
    private CurrentSMDReqDTO current_SMD;
    private AvgMMDReqDTO avg_MMD;
}
