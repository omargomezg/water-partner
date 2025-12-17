package com.hardnets.coop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 10;

}
