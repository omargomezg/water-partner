package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.municipal.BeneficiaryDto;
import com.hardnets.coop.model.dto.municipal.MunicipalReportDto;
import com.hardnets.coop.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/municipal/{period}")
    public ResponseEntity<MunicipalReportDto> getMunicipalReport(@PathVariable Long period,
                                                                 @RequestParam int pageIndex,
                                                                 @RequestParam int pageSize) {
        return ResponseEntity.ok(reportService.getMunicipalReportByPeriod(period, pageIndex, pageSize));
    }

    @GetMapping("/municipal/export")
    public ResponseEntity<?> downloadMunicipalReport() {
        return ResponseEntity.ok().build();
    }

}
