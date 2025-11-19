package com.hardnets.coop.convert;

import com.hardnets.coop.model.dto.items.ItemDto;
import com.hardnets.coop.model.entity.ItemEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ItemEntityToItemDto implements Converter<ItemEntity, ItemDto> {

    @Override
    public ItemDto convert(@NonNull ItemEntity itemEntity) {
        return ItemDto.builder()
                .id(itemEntity.getId())
                .amount(itemEntity.getAmount())
                .description(itemEntity.getDescription())
                .excerpt(itemEntity.getExcerpt())
                .methodOfCalculating(itemEntity.getMethodOfCalculating().name())
                .isFixedAmount(itemEntity.getIsFixedAmount())
                .isActive(itemEntity.getIsActive())
                .typeOfClient(itemEntity.getDescription())
                .build();
    }
}
