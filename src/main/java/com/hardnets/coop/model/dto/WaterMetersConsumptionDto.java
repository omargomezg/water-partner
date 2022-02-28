package com.hardnets.coop.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class WaterMetersConsumptionDto {
    private Long id;
    private Integer serial;
    private String diameter;
    private Date dischargeDate;
    private String sector;
    private String client = "";
    private Integer reading;
}
