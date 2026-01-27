package com.hardnets.coop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;

import com.hardnets.coop.convert.CalculationTypeEnumToDto;
import com.hardnets.coop.convert.ClientTypeEnumToDto;
import com.hardnets.coop.convert.ItemDtoToItemEntity;
import com.hardnets.coop.convert.ItemEntityToItemDto;
import com.hardnets.coop.convert.PeriodDtoToEntity;
import com.hardnets.coop.convert.TariffDtoToEntity;

@Configuration
public class ConverterConfig implements FormatterRegistrar {
	@Override
	public void registerFormatters(@NonNull FormatterRegistry formatterRegistry) {
		formatterRegistry.addConverter(new TariffDtoToEntity());
		formatterRegistry.addConverter(new PeriodDtoToEntity());
		formatterRegistry.addConverter(new ItemEntityToItemDto());
		formatterRegistry.addConverter(new ItemDtoToItemEntity());
		formatterRegistry.addConverter(new CalculationTypeEnumToDto());
		formatterRegistry.addConverter(new ClientTypeEnumToDto());
	}
}
