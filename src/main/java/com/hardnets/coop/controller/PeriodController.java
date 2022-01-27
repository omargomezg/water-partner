package com.hardnets.coop.controller;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.service.PeriodService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Log4j2
@RestController
@RequestMapping("/v1/period")
public class PeriodController {

    private final PeriodService periodService;

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

}
