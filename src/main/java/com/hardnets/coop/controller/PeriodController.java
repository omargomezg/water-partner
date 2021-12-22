package com.hardnets.coop.controller;

import java.util.Optional;
import java.util.Set;

import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.service.ConsumptionService;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.SaleDocumentService;
import com.hardnets.coop.service.TariffService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/period")
public class PeriodController {

    private final PeriodService periodService;
    private final SaleDocumentService<BillEntity> billService;
    private final ConsumptionService consumptionService;
    private final TariffService tariffService;

    /**
     * Listado de periodo
     *
     * @return una lista de periodo
     */
    @GetMapping
    public ResponseEntity<Set<PeriodDto>> list(@RequestParam(name = "status", required = false) String status) {
        var periods = periodService.findAll(
                status != null ? Optional.of(PeriodStatusEnum.valueOf(status)) : Optional.empty()
        );
        return ResponseEntity.ok(periods);
    }

    /**
     * Crea un periodo
     *
     * @return El periodo creado
     */
    @PostMapping
    public ResponseEntity<PeriodDto> create(@RequestBody PeriodDto periodDto) {
        var result = periodService.create(periodDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Actualiza un periodo
     *
     * @return El periodo actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<PeriodDto> update(@PathVariable Long id, @RequestBody PeriodDto periodDto) {
        var result = periodService.update(periodDto);
        return ResponseEntity.ok(result);
    }

    /**
     * Cierra un periodo y abre uno nuevo con fecha al dia siguiente
     *
     * @param id Id del periodo actual
     * @return No devuelve nada
     */
    @PutMapping("/close/{id}")
    public ResponseEntity<String> closePeriod(@PathVariable Long id) {
        if (!tariffService.hasTariffForAllDiameters()) {
            throw new TariffNotFoundException("No existen tarifas para generar cierre");
        }
        PeriodEntity newPeriod = periodService.close(id);
        consumptionService.createAllRecords(newPeriod.getId());
        billService.createAllInPeriod(id);
        return ResponseEntity.ok().build();
    }

}
