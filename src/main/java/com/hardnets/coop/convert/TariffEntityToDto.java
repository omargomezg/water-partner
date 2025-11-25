package com.hardnets.coop.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;

@Component
public class TariffEntityToDto implements Converter<TariffEntity, TariffDto> {
    @Override
    public TariffDto convert(@NonNull TariffEntity source) {
        return TariffDto.builder()
                .id(source.getId())
                .cubicMeter(source.getCubicMeter())
                .flatFee(source.getFlatFee())
                .clientType(source.getClientType().getId())
                .diameter(source.getDiameter().getValue())
                .status(source.getStatus().name())
                .build();
    }
}
