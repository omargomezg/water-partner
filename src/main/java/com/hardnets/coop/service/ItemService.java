package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.items.ItemDto;
import com.hardnets.coop.model.dto.items.ItemsDto;

public interface ItemService {
    ItemsDto getAll(Integer pageIndex, Integer pageSize);

    ItemDto create(ItemDto itemDto);

    ItemDto update();

    ItemDto delete();
}
