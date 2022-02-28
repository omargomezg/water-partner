package com.hardnets.coop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ListOfWaterMeterDto {
    private Long totalHits = 0L;
    private List<WaterMeterDto> contents = new ArrayList<>();
}
