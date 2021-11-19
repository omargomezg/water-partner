package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.AllTariffsBaseDto;
import com.hardnets.coop.model.dto.AllTariffsDto;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

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
        TariffEntity tariff = new TariffEntity();
        tariff.setCubicMeter(tariffDto.getCubicMeter());
        tariff.setFlatFee(tariffDto.getFlatFee());
        if (tariffDto.getClientType() != null) {
            tariff.setClientType(ClientTypeEnum.valueOf(tariffDto.getClientType().toUpperCase()));
        }
        tariff.setLastUpdate(Instant.now());
        tariff.setDiameter(DiameterEnum.valueOf(tariffDto.getDiameter()));
        TariffEntity dbTariff = tariffRepository.save(tariff);
        return new TariffDto(dbTariff);
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
        TariffEntity result = tariffRepository.save(dbTariff);
        return new TariffDto(result);
    }

    @Override
    public boolean hasTariffForAllDiameters() {
        var hastTariff = true;
        var diameters = waterMeterRepository.findAllDiameters();
        for (DiameterEnum diameter: diameters) {
            if(tariffRepository.findFirstByDiameter(diameter).isEmpty()) {
                hastTariff = false;
            }
        }
        return hastTariff;
    }
}
