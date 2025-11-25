package com.hardnets.coop.model.dto;

import java.time.Instant;

import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.entity.TariffEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AllTariffsDto {
    private Long id;
    private Integer diameter;
    private Integer flatFee;
    private Float cubicMeter;
    private String clientType;
    private Instant lastUpdate;
    private String status;

    public AllTariffsDto(TariffEntity tariffEntity) {
        setId(tariffEntity.getId());
        setClientType(tariffEntity.getClientType().getDescription());
        setCubicMeter(tariffEntity.getCubicMeter());
        setFlatFee(tariffEntity.getFlatFee());
        setDiameter(tariffEntity.getDiameter().getValue());
        setStatus(tariffEntity.getStatus() == null ? StatusEnum.ACTIVE.name() : tariffEntity.getStatus().name());
        setLastUpdate(tariffEntity.getLastUpdate());
    }
}
