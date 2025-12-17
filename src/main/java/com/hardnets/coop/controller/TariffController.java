package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.AllTariffsBaseDto;
import com.hardnets.coop.model.dto.AllTariffsDto;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.dto.TariffFilterRequest;
import com.hardnets.coop.service.TariffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tariff")
public class TariffController {

    private final TariffService tariffService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<AllTariffsBaseDto> getAllTariffs(TariffFilterRequest filter) {
        var tariffs = tariffService.getAll(filter);
        var dto = new AllTariffsBaseDto();
        dto.setTariffs(tariffs.stream().map(x -> modelMapper.map(x, AllTariffsDto.class)).toList());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TariffDto> crateTariff(@RequestBody @Valid TariffDto tariff) {
        return new ResponseEntity<>(tariffService.create(tariff), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TariffDto> getTariffById(@PathVariable Long id) {
        return ResponseEntity.ok(tariffService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TariffDto> updateTariff(@PathVariable Long id, @RequestBody @Valid TariffDto tariff) {
        tariff.setId(id);
        return ResponseEntity.ok(tariffService.update(tariff));
    }

}
