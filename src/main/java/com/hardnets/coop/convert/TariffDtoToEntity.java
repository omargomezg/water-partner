package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
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
                .clientType(getClientType(source))
                .lastUpdate(Instant.now())
                .status(StatusEnum.valueOf(source.getStatus()))
                .diameter(DiameterEnum.valueOf(source.getDiameter()))
                .build();
    }

    private ClientTypeEnum getClientType(TariffDto tariffDto) {
        return tariffDto.getClientType() != null ?
                ClientTypeEnum.valueOf(tariffDto.getClientType().toUpperCase()) :
                null;
    }
}
