package com.hardnets.coop.convert;

import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class TariffEntityToDto implements Converter<WaterMeterEntity, WaterMeterDto> {
    @Override
    public WaterMeterDto convert(@NonNull WaterMeterEntity source) {
        return null;
    }
}
