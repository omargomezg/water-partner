package com.hardnets.coop.dto;

import com.hardnets.coop.entity.WaterMeterEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WaterMeterDto {
    private String number;
    private String trademark;
    private Long sizeId;
    private String comment;
    private String sector;
    private Date updated;

    /**
     * Date when is related to
     */
    private Date dischargeDate;

    public WaterMeterDto(WaterMeterEntity waterMeter) {
        this.setNumber(waterMeter.getNumber());
        this.setTrademark(waterMeter.getTrademark());
        this.setDischargeDate(waterMeter.getCreated());
        this.setComment(waterMeter.getDescription());
        this.setSizeId(waterMeter.getSize().getId());
        this.setSector(waterMeter.getSector());
        this.setUpdated(waterMeter.getUpdated());
    }
}
