package com.hardnets.coop.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.entity.WaterMeterEntity;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class WaterMeterEntityToDto implements Converter<WaterMeterEntity, WaterMeterDTO> {
	@Override
	public WaterMeterDTO convert(@NonNull WaterMeterEntity meter) {
		log.info(meter);
		return WaterMeterDTO.builder().id(meter.getId()).serial(meter.getSerial()).trademark(meter.getTrademark())
				.diameter(meter.getDiameter()).comment(meter.getDescription()).sector(meter.getSector())
				.updatedAt(meter.getUpdatedAt() == null ? meter.getCreatedAt() : meter.getUpdatedAt()).build();
	}
}
