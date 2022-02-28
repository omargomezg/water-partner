package com.hardnets.coop.model.dto.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDto {

    private Long totalHits = 0L;

    private List<ItemDto> contents = new ArrayList<>();
}
