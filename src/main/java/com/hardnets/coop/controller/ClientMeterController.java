package com.hardnets.coop.controller;


import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.dto.response.RelatedWaterMetersDto;
import com.hardnets.coop.service.ClientMeterService;
import com.hardnets.coop.service.impl.WaterMeterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/client/water-meter")
public class ClientMeterController {
    private final WaterMeterService waterMeterService;
    private final ClientMeterService clientMeterService;

    @PostMapping("/{dni}")
    public ResponseEntity<Void> addWaterMeter(@RequestBody WaterMeterDTO waterMeterDto,
                                              @PathVariable String dni) {
        waterMeterService.relateToClient(waterMeterDto, dni);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Collection<RelatedWaterMetersDto>> getWaterMeters(@PathVariable String dni) {
        return ResponseEntity.ok(waterMeterService.getByUser(dni));
    }

    @DeleteMapping("/{dni}")
    public void deleteRelation(@PathVariable String dni, @RequestParam Long meterId) {
        clientMeterService.delete(meterId, dni);
    }
}
