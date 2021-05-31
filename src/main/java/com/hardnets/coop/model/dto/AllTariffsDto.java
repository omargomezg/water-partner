package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.entity.TariffEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class AllTariffsDto {
    private Long id;
    private String waterMeterSize;
    private Integer flatFee;
    private BigDecimal cubicMeter;
    private String clientType;
    private Instant lastUpdate;

    public AllTariffsDto(TariffEntity tariffEntity) {
        setId(tariffEntity.getId());
        setClientType(tariffEntity.getClientType().getValue());
        setCubicMeter(tariffEntity.getCubicMeter());
        setFlatFee(tariffEntity.getFlatFee());
        setWaterMeterSize(tariffEntity.getSize().getValue());
        setLastUpdate(tariffEntity.getLastUpdate());
    }
}
