package com.hardnets.coop.dto;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenericListDto {
    private Long id;

    @Nullable
    private String value = "";

    @Nullable
    private String code = "";

    public GenericListDto(Long id) {
        setId(id);
    }
}
