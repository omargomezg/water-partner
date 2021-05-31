package com.hardnets.coop.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsumptionClientDto {
    private String fullName;
    private List<ConsumptionClientDetailDto> content;
    private Long totalHits;
}
