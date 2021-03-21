package com.hardnets.coop.controller;

import com.hardnets.coop.dto.ReadingsDto;
import com.hardnets.coop.dto.WaterMetersConsumptionDto;
import com.hardnets.coop.dto.response.PeriodDto;
import com.hardnets.coop.dto.response.ResumeConsumptionDto;
import com.hardnets.coop.entity.PeriodEntity;
import com.hardnets.coop.service.ConsumptionService;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.WaterMeterService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Api("All client operations")
@RestController
public class ConsumptionController {

    private final WaterMeterService waterMeterService;
    private final ConsumptionService consumptionService;
    private final PeriodService periodService;

    public ConsumptionController(WaterMeterService waterMeterService, ConsumptionService consumptionService,
                                 PeriodService periodService) {
        this.waterMeterService = waterMeterService;
        this.consumptionService = consumptionService;
        this.periodService = periodService;
    }

    @GetMapping("/v1/consumption/related-water-meters")
    public ResponseEntity<Collection<WaterMetersConsumptionDto>> findWaterMeters(@RequestParam Optional<String> waterMeterNumber, @RequestParam Optional<String> rut) {
        if (waterMeterNumber.isPresent() && rut.isPresent()) {
            return new ResponseEntity<>(waterMeterService.findRelatedByNumberOrRut(waterMeterNumber.get(), rut.get()), HttpStatus.OK);
        }
        return waterMeterNumber.map(aString -> new ResponseEntity<>(waterMeterService.findRelatedByNumber(aString), HttpStatus.OK))
                .orElseGet(() -> rut.map(s -> new ResponseEntity<>(waterMeterService.findRelatedByRut(s), HttpStatus.OK))
                        .orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK)));
    }

    @GetMapping("/v1/consumption/{id}")
    public ResponseEntity<List<ReadingsDto>> findConsumptions(@PathVariable Long id) {
        return new ResponseEntity<>(consumptionService.findRecordsByWaterMeterId(id), HttpStatus.OK);
    }

    @PostMapping("/v1/consumption/{id}")
    public ResponseEntity<?> create(@PathVariable Long id, @RequestParam Long consumption) {
        PeriodEntity period = periodService.findByStatus("ACTIVE");
        return new ResponseEntity<>(consumptionService.create(id, consumption, period), HttpStatus.CREATED);
    }

    /**
     * Get resume by period id
     *
     * @param id the primary key of period
     * @return
     */
    @GetMapping("/v1/consumption/period/{id}/detail-resume")
    public ResponseEntity<List<ResumeConsumptionDto>> getPeriodResumeByPeriodId(@PathVariable Long id) {
        return new ResponseEntity(consumptionService.findAllByPeriodId(id), HttpStatus.OK);
    }

    /**
     * Get Periods by year
     *
     * @param year The period start date
     * @return
     */
    @GetMapping("/v1/consumption/period/year")
    public ResponseEntity<Set<PeriodDto>> getAllPeriodYears(@RequestParam Integer year) {
        return new ResponseEntity(periodService.findAllByYear(year), HttpStatus.OK);
    }

    /**
     * Get only years in period extracted
     *
     * @return
     */
    @GetMapping("/v1/consumption/period/years")
    public ResponseEntity<Set<Integer>> getAllPeriods() {
        return new ResponseEntity(periodService.findAllYears(), HttpStatus.OK);
    }

}
