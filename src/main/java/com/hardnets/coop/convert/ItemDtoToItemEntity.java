package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.CalculationTypeEnum;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.items.ItemDto;
import com.hardnets.coop.model.entity.ItemEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoToItemEntity implements Converter<ItemDto, ItemEntity> {

    @Override
    public ItemEntity convert(ItemDto itemDto) {
        return new ItemEntity(
                itemDto.getDescription(),
                itemDto.getExcerpt(),
                itemDto.getAmount(),
                getCalculationType(itemDto.getMethodOfCalculating()),
                itemDto.getIsFixedAmount(),
                itemDto.getIsActive(),
                getClientType(itemDto.getTypeOfClient())
        );
    }

    private ClientTypeEnum getClientType(String type) {
        return ClientTypeEnum.valueOf(type);
    }

    private CalculationTypeEnum getCalculationType(String type) {
        return CalculationTypeEnum.valueOf(type);
    }

}
