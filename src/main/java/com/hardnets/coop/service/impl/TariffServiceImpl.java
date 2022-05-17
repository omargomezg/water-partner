package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ResourceExistsException;
import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.AllTariffsBaseDto;
import com.hardnets.coop.model.dto.AllTariffsDto;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;
    private final WaterMeterRepository waterMeterRepository;
    private final ConversionService conversionService;

    @Override
    public TariffDto findById(Long id) {
        TariffEntity tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not exists"));
        return new TariffDto(tariff);
    }

    @Override
    public AllTariffsBaseDto getAll() {
        List<AllTariffsDto> allTariffs = new ArrayList<>();
        AllTariffsBaseDto baseTariff = new AllTariffsBaseDto();
        tariffRepository.findAll().forEach(tariffEntity -> allTariffs.add(new AllTariffsDto(tariffEntity)));
        var tariffs = allTariffs.stream().sorted(Comparator.comparing(AllTariffsDto::getLastUpdate).reversed())
                .collect(Collectors.toList());
        baseTariff.setTariffs(tariffs);
        baseTariff.setAllRatesAreConfigured(hasTariffForAllDiameters());
        return baseTariff;
    }

    @Override
    public TariffDto create(TariffDto tariffDto) {
        if (existsTariff(tariffDto))
            throw new ResourceExistsException("Tariff already exists");
        TariffEntity tariff = conversionService.convert(tariffDto, TariffEntity.class);
        return conversionService.convert(tariffRepository.save(tariff), TariffDto.class);
    }

    @Override
    public TariffDto update(TariffDto tariffDto) {
        TariffEntity dbTariff = tariffRepository.findById(tariffDto.getId()).orElseThrow(
                () -> new TariffNotFoundException(String.format("Tariff not with id %s found", tariffDto.getId()))
        );
        dbTariff.setCubicMeter(tariffDto.getCubicMeter());
        dbTariff.setClientType(ClientTypeEnum.valueOf(tariffDto.getClientType()));
        dbTariff.setFlatFee(tariffDto.getFlatFee());
        dbTariff.setLastUpdate(Instant.now());
        dbTariff.setDiameter(DiameterEnum.valueOf(tariffDto.getDiameter()));
        dbTariff.setStatus(StatusEnum.valueOf(tariffDto.getStatus()));
        TariffEntity result = tariffRepository.save(dbTariff);
        return new TariffDto(result);
    }

    @Override
    public boolean hasTariffForAllDiameters() {
        var hastTariff = true;
        var diameters = waterMeterRepository.findAllDiameters();
        for (DiameterEnum diameter : diameters) {
            if (tariffRepository.findFirstByDiameter(diameter).isEmpty()) {
                hastTariff = false;
            }
        }
        return hastTariff;
    }

    private boolean existsTariff(TariffDto tariff) {
        return tariffRepository.findBySizeAndClientType(
                DiameterEnum.valueOf(tariff.getDiameter()),
                ClientTypeEnum.valueOf(tariff.getClientType())).isPresent();
    }
}
