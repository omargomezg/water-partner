package com.hardnets.coop.service;

import com.hardnets.coop.dto.AllTariffsDto;
import com.hardnets.coop.dto.TariffDto;
import com.hardnets.coop.entity.DropDownListEntity;
import com.hardnets.coop.entity.TariffEntity;
import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TariffService {

    @Autowired
    TariffRepository tariffRepository;

    @Autowired
    DropDownListRepository dropDownListRepository;

    public List<AllTariffsDto> getAll() {
        List<AllTariffsDto> allTariffs = new ArrayList<>();
        tariffRepository.findAll().forEach(tariffEntity -> allTariffs.add(new AllTariffsDto(tariffEntity)));
        return allTariffs.stream().sorted(Comparator.comparing(AllTariffsDto::getLastUpdate))
                .collect(Collectors.toList());
    }

    public TariffDto create(TariffDto tariffDto) {
        DropDownListEntity clientType = dropDownListRepository.findById(tariffDto.getClientId()).orElseThrow(
                () -> new TariffNotFoundException(String.format("Client type with id %s found", tariffDto.getClientId()))
        );
        DropDownListEntity size = dropDownListRepository.findById(tariffDto.getSizeId()).orElseThrow(
                () -> new TariffNotFoundException(String.format("Size with id %s found", tariffDto.getSizeId()))
        );
        TariffEntity tariff = new TariffEntity();
        tariff.setCubicMeter(tariffDto.getCubicMeter());
        tariff.setFlatFee(tariffDto.getFlatFee());
        tariff.setClientType(clientType);
        tariff.setLastUpdate(Instant.now());
        tariff.setSize(size);
        TariffEntity dbTariff = tariffRepository.save(tariff);
        return new TariffDto(dbTariff);
    }

    public TariffEntity update(TariffEntity tariffEntity) {
        TariffEntity dbTariff = tariffRepository.findById(tariffEntity.getId()).orElseThrow(
                () -> new TariffNotFoundException(String.format("Tariff not with id %s found", tariffEntity.getId()))
        );
        dbTariff.setCubicMeter(tariffEntity.getCubicMeter());
        dbTariff.setClientType(tariffEntity.getClientType());
        dbTariff.setFlatFee(tariffEntity.getFlatFee());
        return tariffRepository.save(dbTariff);
    }
}
