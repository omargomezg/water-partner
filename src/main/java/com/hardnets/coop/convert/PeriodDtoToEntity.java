package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PeriodDtoToEntity implements Converter<PeriodDto, PeriodEntity> {
    @Override
    public PeriodEntity convert(@NonNull PeriodDto periodDto) {
        var periodEntity = new PeriodEntity();
        periodEntity.setStartDate(periodDto.getStartDate());
        periodEntity.setEndDate(periodDto.getEndDate());
        periodEntity.setStatus(PeriodStatusEnum.valueOf(periodDto.getStatus()));
        return null;
    }
}
