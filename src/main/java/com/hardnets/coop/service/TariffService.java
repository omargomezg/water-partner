package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.AllTariffsBaseDto;
import com.hardnets.coop.model.dto.TariffDto;

public interface TariffService {

    TariffDto findById(Long id);

    AllTariffsBaseDto getAll();

    TariffDto create(TariffDto tariffDto);

    TariffDto update(TariffDto tariffDto);

    boolean hasTariffForAllDiameters();
}
