package com.hardnets.coop.controller;

import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.SaleDocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PeriodController {

    private final PeriodService periodService;
    private final SaleDocumentService<BillEntity> billService;

    /**
     * Cierra un periodo y abre uno nuevo con fecha al dia siguiente
     *
     * @param id Id del periodo actual
     * @return No devuelve nada
     */
    @PutMapping("/v1/period/{id}")
    public ResponseEntity<String> closePeriod(@PathVariable Long id) {
        periodService.close(id);
        billService.createAllInPeriod(id);
        return ResponseEntity.ok().build();
    }

}
