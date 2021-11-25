package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.ListOfWaterMeterDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ListOfWaterMeterDto> getWaterMeters(@RequestParam Integer pageIndex,
                                                              @RequestParam Integer pageSize) {
        return ResponseEntity.ok(waterMeterService.getAllByPage(pageIndex, pageSize));
    }

    @GetMapping("/v1/water-meter/not-related")
    public ResponseEntity<Collection<WaterMeterDto>> getNotRelated() {
        return ResponseEntity.ok(waterMeterService.findAllWhereNotRelated());
    }

    @PostMapping("/v1/water-meter")
    public ResponseEntity<WaterMeterDto> addWaterMeter(@RequestBody @Valid WaterMeterDto waterMeter) {
        return new ResponseEntity<>(waterMeterService.create(waterMeter), HttpStatus.CREATED);
    }

    @PutMapping("/v1/water-meter/{id}")
    public ResponseEntity<WaterMeterDto> addWaterMeter(@PathVariable Long id, @RequestBody @Valid WaterMeterDto waterMeter) {
        return ResponseEntity.ok(waterMeterService.update(waterMeter));
    }

    /**
     * Permite actualizar varios medidores con una sola peticion
     *
     * @param meters Una lista de medidores
     * @return
     */
    @PutMapping("/v1/water-meter/massive")
    public ResponseEntity<?> update(@RequestBody @Valid List<WaterMeterDto> meters) {
        waterMeterService.update(meters);
        return ResponseEntity.ok("");
    }

    /**
     * Get An water meter by number
     *
     * @param id The id for water meter
     * @return A water meter
     */
    @GetMapping("/v1/water-meter/{id}")
    public ResponseEntity<WaterMeterDto> getWaterMeterByNumber(@PathVariable Long id) {
        WaterMeterDto waterMeter = new WaterMeterDto(waterMeterService.getById(id));
        return ResponseEntity.ok(waterMeter);
    }
}
