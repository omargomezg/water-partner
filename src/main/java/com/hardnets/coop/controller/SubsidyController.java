package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.response.SubsidyDto;
import com.hardnets.coop.service.SubsidyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@AllArgsConstructor
@RestController
public class SubsidyController {

    private final SubsidyService subsidyService;

    @GetMapping("/v1/subsidy")
    public ResponseEntity<SubsidyDto> getSubsidy(@RequestParam Long id) {
        return ResponseEntity.ok(subsidyService.findByWaterMeterId(id));
    }

    @PutMapping("/v1/subsidy/water-meter")
    public ResponseEntity<?> updateClientSubsidy(@RequestBody @Valid SubsidyDto subsidy) {
        subsidyService.update(subsidy);
        return ResponseEntity.ok().build();
    }

}
