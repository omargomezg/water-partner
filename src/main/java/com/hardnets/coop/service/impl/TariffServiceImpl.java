package com.hardnets.coop.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.hardnets.coop.exception.ResourceExistsException;
import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.AllTariffsBaseDto;
import com.hardnets.coop.model.dto.AllTariffsDto;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.TariffService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;
    private final WaterMeterRepository waterMeterRepository;
    private final ClientTypeRepository clientTypeRepository;;
    private final ConversionService conversionService;

    @Override
    public TariffDto findById(Long id) {
        TariffEntity tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not exists"));
        return conversionService.convert(tariff, TariffDto.class);
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
                () -> new TariffNotFoundException(String.format("Tariff not with id %s found", tariffDto.getId())));
        var clientType = clientTypeRepository.findById(tariffDto.getClientType())
                .orElseThrow(() -> new TariffNotFoundException("Client type not found"));
        dbTariff.setCubicMeter(tariffDto.getCubicMeter());
        dbTariff.setClientType(clientType);
        dbTariff.setFlatFee(tariffDto.getFlatFee());
        dbTariff.setLastUpdate(Instant.now());
        dbTariff.setDiameter(DiameterEnum.valueOfLabel(tariffDto.getDiameter()));
        dbTariff.setStatus(StatusEnum.valueOf(tariffDto.getStatus()));
        TariffEntity result = tariffRepository.save(dbTariff);
        return conversionService.convert(result, TariffDto.class);
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
        var clientType = clientTypeRepository.findById(tariff.getClientType())
                .orElseThrow(() -> new TariffNotFoundException("Client type not found"));
        var diameter = DiameterEnum.valueOfLabel(tariff.getDiameter());
        return tariffRepository.findBySizeAndClientType(diameter, clientType).isPresent();
    }
}
