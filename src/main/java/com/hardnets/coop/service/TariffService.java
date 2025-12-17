package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.dto.TariffFilterRequest;
import com.hardnets.coop.model.entity.TariffEntity;

import java.util.List;

public interface TariffService {

    TariffDto findById(Long id);

    List<TariffEntity> getAll(TariffFilterRequest filter);

    TariffDto create(TariffDto tariffDto);

    TariffDto update(TariffDto tariffDto);

    boolean hasTariffForAllDiameters();
}
