package com.hardnets.coop.controller;

import com.hardnets.coop.service.PeriodService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PeriodController {

    private final PeriodService periodService;

    @PutMapping("/v1/period/{id}")
    public ResponseEntity<String> closePeriod(@PathVariable Long id) {
        periodService.closePeriod(id);
        return ResponseEntity.ok().build();
    }

}
