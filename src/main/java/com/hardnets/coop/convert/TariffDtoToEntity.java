package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TariffDtoToEntity implements Converter<TariffDto, TariffEntity> {
    @Override
    public TariffEntity convert(@NonNull TariffDto source) {
        return TariffEntity.builder()
                .flatFee(source.getFlatFee())
                .cubicMeter(source.getCubicMeter())
                .lastUpdate(Instant.now())
                .status(StatusEnum.valueOf(source.getStatus()))
                .diameter(source.getDiameter())
                .build();
    }
}
