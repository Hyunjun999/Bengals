package com.bengals.redistricting_project.Compare;

import com.bengals.redistricting_project.Compare.DTO.CompareResDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompareController {
    private final CompareService compareService;

    public CompareController(CompareService compareService) {
        this.compareService = compareService;
    }

    @GetMapping("/{state}/compare")
    public CompareResDTO getCompareResDTO(@PathVariable String state) {
        return compareService.findCompare(state);
    }
}
