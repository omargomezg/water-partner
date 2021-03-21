package com.hardnets.coop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WaterMetersConsumptionDto {
    private Long id;
    private String number;
    private String millimeters;
    private Date dischargeDate;
    private String sector;
    private String client;
}
