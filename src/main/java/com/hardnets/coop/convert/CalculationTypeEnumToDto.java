package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.CalculationTypeEnum;
import com.hardnets.coop.model.dto.values.CalculationTypeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CalculationTypeEnumToDto implements Converter<CalculationTypeEnum, CalculationTypeDto> {
    @Override
    public CalculationTypeDto convert(CalculationTypeEnum calculationTypeEnum) {
        return CalculationTypeDto.builder()
                .name(calculationTypeEnum.name())
                .translateEs(calculationTypeEnum.spanish)
                .build();
    }
}
