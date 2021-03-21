package com.hardnets.coop.dto;

import com.hardnets.coop.entity.TariffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    private Long id;
    private BigDecimal cubicMeter;
    private Integer flatFee;
    private Long clientId;
    private Long sizeId;

    public TariffDto(TariffEntity tariffEntity) {
        setCubicMeter(tariffEntity.getCubicMeter());
        setClientId(tariffEntity.getClientType().getId());
        setFlatFee(tariffEntity.getFlatFee());
        setId(tariffEntity.getId());
        setSizeId(tariffEntity.getSize().getId());
    }
}
