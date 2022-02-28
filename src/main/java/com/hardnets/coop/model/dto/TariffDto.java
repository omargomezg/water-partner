package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.entity.TariffEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    private Long id;
    private Float cubicMeter;
    private Integer flatFee;
    private String clientType;
    private String diameter;

    public TariffDto(TariffEntity tariffEntity) {
        setCubicMeter(tariffEntity.getCubicMeter());
        setClientType(tariffEntity.getClientType().label);
        setFlatFee(tariffEntity.getFlatFee());
        setId(tariffEntity.getId());
        setDiameter(tariffEntity.getDiameter().name());
    }
}
