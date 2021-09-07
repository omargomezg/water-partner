package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.DiameterEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaterMetersConsumptionDto {
    private Long id;
    private String number;
    private DiameterEnum diameter;
    private Date dischargeDate;
    private String sector;
    private String client;
    private Long consumption;
}
