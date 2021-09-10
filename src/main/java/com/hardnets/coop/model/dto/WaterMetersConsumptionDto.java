package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.DiameterEnum;
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

    public WaterMetersConsumptionDto(Long id, Integer serial, DiameterEnum diameter, Date dischargeDate, String sector,
                                     Integer reading) {
        this.id = id;
        this.serial = serial;
        this.diameter = diameter.name();
        this.dischargeDate = dischargeDate;
        this.sector = sector;
        this.reading = reading;
    }
}
