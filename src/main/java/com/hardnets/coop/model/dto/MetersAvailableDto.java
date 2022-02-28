package com.hardnets.coop.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MetersAvailableDto {
    private List<WaterMeterDto> meters = new ArrayList<>();
    private Long totalHits = 0L;
}
