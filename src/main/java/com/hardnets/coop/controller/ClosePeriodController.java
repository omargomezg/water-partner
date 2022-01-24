package com.hardnets.coop.controller;

import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.service.ConsumptionService;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.SaleDocumentService;
import com.hardnets.coop.service.TariffService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/v1/closing-period")
public class ClosePeriodController {

    private final TariffService tariffService;
    private final PeriodService periodService;
    private final ConsumptionService consumptionService;
    private final SaleDocumentService<BillEntity> billService;

    /**
     * Cierra un periodo y abre uno nuevo con fecha al dia siguiente
     *
     * @param period Periodo
     * @return No devuelve nada
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> closePeriod(@RequestBody @Valid PeriodDto period, @PathVariable Long id) {
        log.info("Cerrando periodo");
        /*if (!tariffService.hasTariffForAllDiameters()) {
            throw new TariffNotFoundException("No existen tarifas para generar cierre");
        }
        PeriodEntity newPeriod = periodService.close(id);
        consumptionService.createAllRecords(newPeriod.getId());
        billService.createAllInPeriod(period.getId());
        log.info("Periodo cerrado {}", period);*/
        return ResponseEntity.ok().build();
    }
}
