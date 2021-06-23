package com.hardnets.coop.controller;

import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.service.ConsumptionService;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.SaleDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class PeriodController {

    private final PeriodService periodService;
    private final SaleDocumentService<BillEntity> billService;
    private final ConsumptionService consumptionService;

    @GetMapping("/v1/period")
    public List<PeriodEntity> getPeriods() {
        return periodService.findAll();
    }

    /**
     * Cierra un periodo y abre uno nuevo con fecha al dia siguiente
     *
     * @param id Id del periodo actual
     * @return No devuelve nada
     */
    @PutMapping("/v1/period/{id}")
    public ResponseEntity<String> closePeriod(@PathVariable Long id) {
        PeriodEntity newPeriod = periodService.close(id);
        consumptionService.createAllRecords(newPeriod.getId());
        billService.createAllInPeriod(id);
        return ResponseEntity.ok().build();
    }

}
