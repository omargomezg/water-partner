package com.hardnets.coop.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

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
