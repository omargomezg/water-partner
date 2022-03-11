package com.hardnets.coop.model.dto;

import lombok.Data;

@Data
public class ClientWaterMeterDto {
    private String dni;
    private Long waterMeterNumber;
    private String comment;
    private Long sizeId;
    private String trademark;
}
