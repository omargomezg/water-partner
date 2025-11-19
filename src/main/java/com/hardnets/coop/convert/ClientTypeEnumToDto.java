package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.values.ClientTypeDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ClientTypeEnumToDto implements Converter<ClientTypeEnum, ClientTypeDto> {
    @Override
    public ClientTypeDto convert(@NonNull ClientTypeEnum clientTypeEnum) {
        return ClientTypeDto.builder()
                .name(clientTypeEnum.name())
                .translateEs(clientTypeEnum.spanish)
                .build();
    }
}
