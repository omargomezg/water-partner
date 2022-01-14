package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TariffDtoToEntity implements Converter<TariffDto, TariffEntity> {
    @Override
    public TariffEntity convert(TariffDto tariffDto) {
        return TariffEntity.builder()
                .flatFee(tariffDto.getFlatFee())
                .cubicMeter(tariffDto.getCubicMeter())
                .clientType(getClientType(tariffDto))
                .lastUpdate(Instant.now())
                .diameter(DiameterEnum.valueOf(tariffDto.getDiameter()))
                .build();
    }

    private ClientTypeEnum getClientType(TariffDto tariffDto) {
        return tariffDto.getClientType() != null ?
                ClientTypeEnum.valueOf(tariffDto.getClientType().toUpperCase()) :
                null;
    }
}
