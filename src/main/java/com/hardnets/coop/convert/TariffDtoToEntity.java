package com.hardnets.coop.convert;

import java.time.Instant;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;

@Component
public class TariffDtoToEntity implements Converter<TariffDto, TariffEntity> {
    @Override
    public TariffEntity convert(@NonNull TariffDto source) {
        return TariffEntity.builder()
                .flatFee(source.getFlatFee())
                .cubicMeter(source.getCubicMeter())
                .lastUpdate(Instant.now())
                .status(StatusEnum.valueOf(source.getStatus()))
                .diameter(DiameterEnum.valueOfLabel(source.getDiameter()))
                .build();
    }
}
