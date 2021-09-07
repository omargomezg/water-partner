package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.entity.TariffEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class AllTariffsDto {
    private Long id;
    private String diameter;
    private Integer flatFee;
    private Float cubicMeter;
    private String clientType;
    private Instant lastUpdate;

    public AllTariffsDto(TariffEntity tariffEntity) {
        setId(tariffEntity.getId());
        setClientType(tariffEntity.getClientType().label);
        setCubicMeter(tariffEntity.getCubicMeter());
        setFlatFee(tariffEntity.getFlatFee());
        setDiameter(tariffEntity.getDiameter().label);
        setLastUpdate(tariffEntity.getLastUpdate());
    }
}
