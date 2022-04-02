package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.AllTariffsBaseDto;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TariffController {

    @Autowired
    TariffService tariffService;

    @GetMapping("/v1/tariff")
    public ResponseEntity<AllTariffsBaseDto> getAllTariffs() {
        return ResponseEntity.ok(tariffService.getAll());
    }

    @PostMapping("/v1/tariff")
    public ResponseEntity<TariffDto> crateTariff(@RequestBody @Valid TariffDto tariff) {
        return new ResponseEntity<>(tariffService.create(tariff), HttpStatus.CREATED);
    }

    @GetMapping("/v1/tariff/{id}")
    public ResponseEntity<TariffDto> getTariffById(@PathVariable Long id) {
        return ResponseEntity.ok(tariffService.findById(id));
    }

    @PutMapping("/v1/tariff/{id}")
    public ResponseEntity<TariffDto> updateTariff(@PathVariable Long id, @RequestBody @Valid TariffDto tariff) {
        tariff.setId(id);
        return ResponseEntity.ok(tariffService.update(tariff));
    }

}
