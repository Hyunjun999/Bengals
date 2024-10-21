package com.bengals.redistricting_project.PA;

import com.bengals.redistricting_project.PA.Collection.PADistrict;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PAController {
    private final PAService paService;

    public PAController(PAService paService) {
        this.paService = paService;
    }

    @GetMapping("/pa/all/districts")
    public List<PADistrict> getAllDistricts() {
        return paService.findAllDistricts();
    }
}
