package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.bulk.BulkWaterMeterUserDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.util.RutUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Service
public class BulkService {

    private final ClientRepository clientRepository;
    private final WaterMeterRepository waterMeterRepository;
    private final ConsumptionRepository consumptionRepository;

    public void addWaterMeterWithUser(List<BulkWaterMeterUserDto> records, PeriodEntity period) {
        records.stream().parallel().forEach(item -> {
            if (RutUtils.validateRut(item.getRut()) && !item.getNames().isEmpty() && Objects.nonNull(item.getSeries())) {
                String rut = item.getRut();
                DiameterEnum diameter = DiameterEnum.valueOf(item.getDiameter().toString());
                ClientEntity client = clientRepository.findByRut(rut).orElseGet(() -> createClient(item, rut));
                WaterMeterEntity waterMeter =
                        waterMeterRepository.findBySerial(item.getSeries().toString()).orElseGet(() -> createWaterMeter(item, diameter));
                waterMeter.setClient(client);
                waterMeterRepository.saveAndFlush(waterMeter);
                saveConsumption(waterMeter, item.getReading(), period);
            }
        });
    }

    private void saveConsumption(WaterMeterEntity waterMeter, Integer reading, PeriodEntity period) {
        ConsumptionEntity consumptionEntity = new ConsumptionEntity();
        consumptionEntity.setCreated(LocalDateTime.now());
        consumptionEntity.setReading(reading);
        consumptionEntity.setReadingDate(new Date());
        consumptionEntity.setWaterMeter(waterMeter);
        consumptionEntity.setPeriod(period);
        consumptionRepository.save(consumptionEntity);
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
        waterMeterEntity.setSerial(item.getSeries().toString());
        waterMeterEntity.setStatus(StatusEnum.NEW);
        waterMeterEntity.setDiameter(diameter);
        return waterMeterRepository.saveAndFlush(waterMeterEntity);
    }

}
