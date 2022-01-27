package com.hardnets.coop.model.dto.items;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ItemDto {
    private Long id;
    private Integer amount;
    private String description;
    private String excerpt;
    private String methodOfCalculating;
    private Boolean isFixedAmount;
    private Boolean isActive;
    private String typeOfClient;
}
