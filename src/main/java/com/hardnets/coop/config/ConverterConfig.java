package com.hardnets.coop.config;

import com.hardnets.coop.convert.ClientDtoToEntity;
import com.hardnets.coop.convert.ClientEntityToDto;
import com.hardnets.coop.convert.PeriodDtoToEntity;
import com.hardnets.coop.convert.TariffDtoToEntity;
import com.hardnets.coop.convert.TariffEntityToDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistrar;
import org.springframework.format.FormatterRegistry;

@Configuration
public class ConverterConfig implements FormatterRegistrar {
    @Override
    public void registerFormatters(FormatterRegistry formatterRegistry) {
        formatterRegistry.addConverter(new ClientEntityToDto());
        formatterRegistry.addConverter(new TariffEntityToDto());
        formatterRegistry.addConverter(new TariffDtoToEntity());
        formatterRegistry.addConverter(new ClientDtoToEntity());
        formatterRegistry.addConverter(new PeriodDtoToEntity());
    }
}
