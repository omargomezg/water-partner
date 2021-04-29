package com.hardnets.coop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenericListDto {
    private Long id;
    private String value = "";
    private String code = "";

    public GenericListDto(Long id) {
        setId(id);
    }
}
