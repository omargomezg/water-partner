package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.bulk.BulkWaterMeterUserDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.util.RutUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class BulkService {

    private final ClientRepository clientRepository;
    private final WaterMeterRepository waterMeterRepository;

    public void addWaterMeterWithUser(List<BulkWaterMeterUserDto> records) {
        records.stream().parallel().forEach(item -> {
            if (RutUtils.validateRut(item.getRut()) && !item.getNames().isEmpty() && Objects.nonNull(item.getSeries())) {
                String rut = item.getRut();
                DiameterEnum diameter = DiameterEnum.getByLabel(item.getDiameter().toString());
                ClientEntity client = clientRepository.findByRut(rut).orElseGet(() -> createClient(item, rut));
                WaterMeterEntity waterMeter =
                        waterMeterRepository.findByNumber(item.getSeries().toString()).orElseGet(() -> createWaterMeter(item, diameter));
                waterMeter.setClient(client);
                waterMeterRepository.saveAndFlush(waterMeter);
            }
        });
    }

    @NotNull
    private ClientEntity createClient(BulkWaterMeterUserDto item, String rut) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setRut(rut);
        clientEntity.setNames(item.getNames());
        clientEntity.setClientType(ClientTypeEnum.PARTNER);
        return clientRepository.saveAndFlush(clientEntity);
    }

    @NotNull
    private WaterMeterEntity createWaterMeter(BulkWaterMeterUserDto item, DiameterEnum diameter) {
        WaterMeterEntity waterMeterEntity = new WaterMeterEntity();
        waterMeterEntity.setNumber(item.getSeries().toString());
        waterMeterEntity.setStatus(StatusEnum.NEW);
        waterMeterEntity.setDiameter(diameter);
        return waterMeterRepository.saveAndFlush(waterMeterEntity);
    }

}
