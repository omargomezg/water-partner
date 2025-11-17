package com.hardnets.coop.model.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericListDto {
    private Long id;

    @Nullable
    private String value;

    @Nullable
    private String code;

}
