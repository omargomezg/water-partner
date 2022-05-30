package com.hardnets.coop.convert;

import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Log4j2
@Component
public class WaterMeterEntityToDto implements Converter<WaterMeterEntity, WaterMeterDto> {
    @Override
    public WaterMeterDto convert(WaterMeterEntity meter) {
       log.info(meter);
        return WaterMeterDto.builder()
                .id(meter.getId())
                .serial(meter.getSerial())
                .trademark(meter.getTrademark())
                .diameter(meter.getDiameter())
                .comment(meter.getDescription())
                .sector(meter.getSector())
                .updated(meter.getUpdated() == null ? meter.getCreated() : meter.getUpdated())
                .build();
    }
}
