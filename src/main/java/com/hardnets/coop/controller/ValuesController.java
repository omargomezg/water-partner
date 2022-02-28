package com.hardnets.coop.controller;

import com.hardnets.coop.model.constant.CalculationTypeEnum;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.values.CalculationTypeDto;
import com.hardnets.coop.model.dto.values.ClientTypeDto;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/values")
public class ValuesController {

    private final ConversionService conversionService;

    @GetMapping("/calculation-type")
    public ResponseEntity<List<CalculationTypeDto>> getAllTypeOfCalculation() {
        var values = Arrays.stream(CalculationTypeEnum.values())
                .map(value -> conversionService.convert(value, CalculationTypeDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(values);
    }

    @GetMapping("/client-type")
    public ResponseEntity<List<ClientTypeDto>> getAllTypeOfClients() {
        var values = Arrays.stream(ClientTypeEnum.values())
                .map(value -> conversionService.convert(value, ClientTypeDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(values);
    }
}
