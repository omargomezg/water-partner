package com.hardnets.coop.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.hardnets.coop.model.dto.ClientDTO;
import com.hardnets.coop.model.dto.TariffFilterRequest;
import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.service.TariffService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientDTOAssembler {

    private final ModelMapper modelMapper;
    private final TariffService tariffService;

    public ClientDTO toModel(ClientEntity clientEntity) {
        var client = modelMapper.map(clientEntity, ClientDTO.class);
        if (clientEntity.getWaterMeters() != null && !clientEntity.getWaterMeters().isEmpty()) {
            client.setWaterMeters(
                    clientEntity.getWaterMeters().stream().map(this::toWaterMeterDTO).toList());
        }
        return client;
    }

    private WaterMeterDTO toWaterMeterDTO(WaterMeterEntity waterMeterEntity) {
        var meter = modelMapper.map(waterMeterEntity, WaterMeterDTO.class);
        var filter = TariffFilterRequest.builder()
                .diameter(waterMeterEntity.getDiameter())
                .clientTypeId(waterMeterEntity.getClient().getClientType().getId())
                .build();
        var tariff = tariffService.getAll(filter).stream().findFirst();
        if (tariff.isPresent()) {
            meter.setFlatFee(tariff.get().getFlatFee());
            meter.setCubicMeter(tariff.get().getCubicMeter());
        }

        waterMeterEntity.getSubsidies().stream()
                .filter(sub -> Boolean.TRUE.equals(sub.getIsActive())
                        && sub.getEndingDate().after(new java.util.Date()))
                .findFirst()
                .ifPresent(sub -> {
                    meter.setSubsidy(new com.hardnets.coop.model.dto.response.SubsidyDto(
                            sub.getId(), sub.getStartDate(), sub.getEndingDate(), sub.getPercentage(),
                            sub.getObservation()));
                });

        return meter;
    }

}
