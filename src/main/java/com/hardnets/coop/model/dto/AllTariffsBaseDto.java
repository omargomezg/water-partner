package com.hardnets.coop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllTariffsBaseDto {
    private Boolean allRatesAreConfigured = false;
    private List<AllTariffsDto> tariffs;
}
