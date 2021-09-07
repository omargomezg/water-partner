package com.hardnets.coop.service;

import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.AllTariffsDto;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TariffService {

    private final TariffRepository tariffRepository;

    public TariffDto findById(Long id) {
        TariffEntity tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not exists"));
        return new TariffDto(tariff);
    }

    public List<AllTariffsDto> getAll() {
        List<AllTariffsDto> allTariffs = new ArrayList<>();
        tariffRepository.findAll().forEach(tariffEntity -> allTariffs.add(new AllTariffsDto(tariffEntity)));
        return allTariffs.stream().sorted(Comparator.comparing(AllTariffsDto::getLastUpdate).reversed())
                .collect(Collectors.toList());
    }

    public TariffDto create(TariffDto tariffDto) {
        TariffEntity tariff = new TariffEntity();
        tariff.setCubicMeter(tariffDto.getCubicMeter());
        tariff.setFlatFee(tariffDto.getFlatFee());
        tariff.setClientType(ClientTypeEnum.valueOf(tariffDto.getClientType()));
        tariff.setLastUpdate(Instant.now());
        tariff.setDiameter(DiameterEnum.valueOf(tariffDto.getDiameter()));
        TariffEntity dbTariff = tariffRepository.save(tariff);
        return new TariffDto(dbTariff);
    }

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
}
