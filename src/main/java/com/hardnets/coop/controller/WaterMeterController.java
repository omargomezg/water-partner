package com.hardnets.coop.controller;

import com.hardnets.coop.dto.WaterMeterDto;
import com.hardnets.coop.service.WaterMeterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Log4j2
@AllArgsConstructor
@RestController
public class WaterMeterController {

    private final WaterMeterService waterMeterService;

    /**
     * List all water meters
     *
     * @return a list of water meters
     */
    @GetMapping("/v1/water-meter")
    public ResponseEntity<Collection<WaterMeterDto>> getWaterMeters() {
        return ResponseEntity.ok(waterMeterService.getAll());
    }

    @GetMapping("/v1/water-meter/not-related")
    public ResponseEntity<Collection<WaterMeterDto>> getNotRelated() {
        return ResponseEntity.ok(waterMeterService.findAllWheregetNotRelated());
    }

    @PostMapping("/v1/water-meter")
    public ResponseEntity<WaterMeterDto> addWaterMeter(@RequestBody @Valid WaterMeterDto waterMeter) {
        return new ResponseEntity<>(waterMeterService.create(waterMeter), HttpStatus.CREATED);
    }

    /**
     * Permite actualizar varios medidores con una sola peticion
     *
     * @param meterDtos Una lista de medidores
     * @return
     */
    @PutMapping("/v1/water-meter/masive")
    public ResponseEntity<?> update(@RequestBody @Valid List<WaterMeterDto> meterDtos) {
        waterMeterService.update(meterDtos);
        return ResponseEntity.ok("");
    }

    /**
     * Get An water meter by number
     *
     * @param number The id for water meter
     * @return A water meter
     */
    @GetMapping("/v1/water-meter/{number}")
    public ResponseEntity<WaterMeterDto> getWaterMeterByNumber(@PathVariable String number) {
        WaterMeterDto waterMeter = waterMeterService.getByNumber(number);
        return ResponseEntity.ok(waterMeter);
    }
}
