package com.hardnets.coop.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenericListDto {
    private Long id;

    private String value;

    private String code;

}
