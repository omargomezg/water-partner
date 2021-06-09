package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.entity.TariffEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    private Long id;
    private Float cubicMeter;
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
