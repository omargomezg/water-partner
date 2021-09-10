package com.hardnets.coop.controller;

import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.ReadingsDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.WaterMetersConsumptionDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDto;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDto;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.service.ConsumptionService;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.WaterMeterService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Api("All client operations")
@AllArgsConstructor
@RestController
public class ConsumptionController {

    private final WaterMeterService waterMeterService;
    private final ConsumptionService consumptionService;
    private final PeriodService periodService;

    /**
     * Obtiene un listado de medidores mas su ultima lectura
     * @param waterMeterNumber
     * @param rut
     * @param consumptionPending
     * @return
     */
    @GetMapping("/v1/consumption/related-water-meters")
    public ResponseEntity<Collection<WaterMetersConsumptionDto>> findWaterMeters(@RequestParam(required = false) String waterMeterNumber,
                                                                                 @RequestParam(required = false) String rut,
                                                                                 @RequestParam(required = false) String sector,
                                                                                 @RequestParam(name = "pending") boolean consumptionPending) {
        return ResponseEntity.ok(waterMeterService.findAllForSetConsumption(waterMeterNumber, rut, sector, consumptionPending));
    }

    /**
     * Obtiene un listado de consumos por cliente consultado
     *
     * @param rut Cliente a consultar
     * @return Un objeto que contiene el total de elementos mas el detalle por cada periodo
     */
    @GetMapping("/v1/consumption/client/{rut}")
    public ResponseEntity<ConsumptionClientDto> consumptionByClient(@PathVariable String rut,
                                                                    @RequestParam Integer pageIndex,
                                                                    @RequestParam Integer pageSize) {
        return ResponseEntity.ok(consumptionService.findAllByClient(rut, pageIndex, pageSize));
    }

    /**
     * @param id Id del medidor
     * @return
     */
    @GetMapping("/v1/consumption/{id}")
    public ResponseEntity<List<ReadingsDto>> findConsumptions(@PathVariable Long id) {
        return new ResponseEntity<>(consumptionService.findRecordsByWaterMeterId(id), HttpStatus.OK);
    }

    @PostMapping("/v1/consumption/{id}")
    public ResponseEntity<String> create(@PathVariable Integer id, @RequestParam Integer consumption) {
        PeriodEntity period = periodService.findByStatus(PeriodStatusEnum.ACTIVE);
        WaterMeterEntity waterMeter = waterMeterService.getBySerial(id);
        Optional<ConsumptionEntity> dbConsumption = consumptionService.findOneByPeriodAndWaterMeter(period.getId(),
                waterMeter.getId());
        if (dbConsumption.isPresent()) {
            dbConsumption.get().setReading(consumption);
            consumptionService.update(dbConsumption.get());
        } else {
            consumptionService.create(waterMeter, consumption, period);
        }
        return ResponseEntity.created(URI.create("/consumption/" + id)).build();
    }

    /**
     * Obtiene un resumen de consumo por periodo
     *
     * @param id El identificador del periodo
     * @return
     */
    @GetMapping("/v1/consumption/period/{id}/detail-resume")
    public ResponseEntity<ResumeConsumptionDto> getPeriodResumeByPeriodId(@PathVariable Long id, @RequestParam int pageIndex,
                                                                          @RequestParam int pageSize) {
        return ResponseEntity.ok(consumptionService.findAllByPeriodId(id, pageIndex, pageSize));
    }

    /**
     * Obtiene periodos de un año en particular
     *
     * @param year Año que se desea consultar
     * @return Una lista de periodos
     */
    @GetMapping("/v1/consumption/period/year")
    public ResponseEntity<Set<PeriodDto>> getAllPeriodYears(@RequestParam Integer year) {
        return ResponseEntity.ok(periodService.findAllByYear(year));
    }

    /**
     * Get only years in period extracted
     *
     * @return una lista de años
     */
    @GetMapping("/v1/consumption/period/years")
    public ResponseEntity<Set<Integer>> getAllPeriods() {
        return ResponseEntity.ok(periodService.findAllYears());
    }

}
