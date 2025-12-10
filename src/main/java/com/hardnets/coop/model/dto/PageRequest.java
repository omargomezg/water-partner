package com.hardnets.coop.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PageRequest {
    @Builder.Default
    private Integer page = 0;

    @Builder.Default
    private Integer size = 0;

    protected PageRequest() {}
}
