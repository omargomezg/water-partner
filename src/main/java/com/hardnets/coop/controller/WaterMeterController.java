package com.hardnets.coop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hardnets.coop.model.dto.MetersAvailableDto;
import com.hardnets.coop.model.dto.PageResponse;
import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.dto.WaterMeterFilterRequest;
import com.hardnets.coop.service.impl.WaterMeterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/water-meter")
public class WaterMeterController {

    private final WaterMeterService waterMeterService;

    /**
     * List all water meters
     *
     * @return a list of water meters
     */
    @GetMapping
    public ResponseEntity<PageResponse<WaterMeterDTO>> getWaterMeters(WaterMeterFilterRequest params) {
        PageResponse<WaterMeterDTO> result = new PageResponse<>();
        var meters = waterMeterService.getAllByPage(params);
        var total = waterMeterService.getTotalOfElements(params);
        result.setContent(meters.stream().map(WaterMeterDTO::new).toList());
        result.setTotalElements(total);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/not-related")
    public ResponseEntity<MetersAvailableDto> getNotRelated(@RequestParam Integer pageIndex,
                                                            @RequestParam Integer pageSize) {
        return ResponseEntity.ok(waterMeterService.findAllWhereNotRelated(pageIndex, pageSize));
    }

    @PostMapping
    public ResponseEntity<WaterMeterDTO> addWaterMeter(@RequestBody @Valid WaterMeterDTO waterMeter) {

        return new ResponseEntity<>(waterMeterService.create(waterMeter), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaterMeterDTO> addWaterMeter(@PathVariable Long id, @RequestBody @Valid WaterMeterDTO waterMeter) {
        return ResponseEntity.ok(waterMeterService.update(waterMeter));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteWaterMeters(@RequestBody List<Long> ids) {
        ids.forEach(waterMeterService::delete);
        return ResponseEntity.accepted().build();
    }

    /**
     * Permite actualizar varios medidores con una sola peticion
     *
     * @param meters Una lista de medidores
     * @return
     */
    @PutMapping("/massive")
    public ResponseEntity<Void> update(@RequestBody @Valid List<WaterMeterDTO> meters) {
        waterMeterService.update(meters);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get An water meter by number
     *
     * @param id The id for water meter
     * @return A water meter
     */
    @GetMapping("/{id}")
    public ResponseEntity<WaterMeterDTO> getWaterMeterByNumber(@PathVariable Long id) {
        WaterMeterDTO waterMeter = new WaterMeterDTO(waterMeterService.getById(id));
        return ResponseEntity.ok(waterMeter);
    }
}
