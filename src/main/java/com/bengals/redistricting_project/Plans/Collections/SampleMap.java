package com.bengals.redistricting_project.Plans.Collections;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "SampleMaps")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SampleMap {
    private String state;
    private String districtType;
    private List<Feature> features;
}
