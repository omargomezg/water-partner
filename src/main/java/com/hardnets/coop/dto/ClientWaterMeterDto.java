package com.hardnets.coop.dto;

import lombok.Data;

@Data
public class ClientWaterMeterDto {
    private String rut;
    private Long waterMeterNumber;
    private String comment;
    private Long sizeId;
    private String trademark;
}
