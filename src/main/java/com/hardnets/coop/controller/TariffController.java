package com.hardnets.coop.controller;

import com.hardnets.coop.dto.AllTariffsDto;
import com.hardnets.coop.dto.TariffDto;
import com.hardnets.coop.entity.TariffEntity;
import com.hardnets.coop.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TariffController {

    @Autowired
    TariffService tariffService;

    @GetMapping("/v1/tariff")
    public ResponseEntity<List<AllTariffsDto>> getAllTariffs() {
        return ResponseEntity.ok(tariffService.getAll());
    }

    @PostMapping("/v1/tariff")
    public ResponseEntity<?> crateTariff(@RequestBody @Valid TariffDto tariff) {
        return new ResponseEntity<>(tariffService.create(tariff), HttpStatus.CREATED);
    }

    @PutMapping("/v1/tariff/{id}")
    public ResponseEntity<?> updateTariff(@RequestParam Long id, @RequestBody @Valid TariffEntity tariff) {
        tariff.setId(id);
        return new ResponseEntity<>(tariffService.update(tariff), HttpStatus.OK);
    }

}
