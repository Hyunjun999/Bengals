package com.bengals.redistricting_project.Plans.Collections;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "SampleMaps")
@Data
public class SampleMap {
    private String state;
    private String districtType;
    private List<Feature> features;
}