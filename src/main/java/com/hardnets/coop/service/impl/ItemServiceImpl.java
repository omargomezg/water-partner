package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ItemException;
import com.hardnets.coop.model.dto.items.ItemDto;
import com.hardnets.coop.model.dto.items.ItemsDto;
import com.hardnets.coop.model.entity.ItemEntity;
import com.hardnets.coop.repository.ItemRepository;
import com.hardnets.coop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ConversionService conversionService;
    private final ItemRepository itemRepository;

    @Override
    public ItemsDto getAll(Integer pageIndex, Integer pageSize) {
        ItemsDto itemsDto = new ItemsDto();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        var items = itemRepository.findAll(pageable);
        itemsDto.setTotalHits(items.getTotalElements());
        itemsDto.setContents(getContent(items));
        return itemsDto;
    }

    private List<ItemDto> getContent(Page<ItemEntity> items) {
        return items.getContent().stream()
                .map(item -> conversionService.convert(item, ItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto create(ItemDto itemDto) {
        if (itemRepository.findById(itemDto.getId()).isPresent()) {
            throw new ItemException();
        }
        var itemEntity = conversionService.convert(itemDto, ItemEntity.class);
        itemEntity = itemRepository.save(itemEntity);
        return conversionService.convert(itemEntity, ItemDto.class);
    }

    @Override
    public ItemDto update() {
        return null;
    }

    @Override
    public ItemDto delete() {
        return null;
    }
}
