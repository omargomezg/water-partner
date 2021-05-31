package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.entity.WaterMeterEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WaterMeterDto {
    private Long id;
    private String number;
    private String trademark;
    private Long sizeId;
    private String comment;
    private String sector;
    private Date updated;
    private String rut;

    /**
     * Date when is related to
     */
    private Date dischargeDate;

    public WaterMeterDto(Long id, String number, String trademark, Long sizeId, String comment, String sector, Date updated) {
        this.id = id;
        this.number = number;
        this.trademark = trademark;
        this.sizeId = sizeId;
        this.comment = comment;
        this.sector = sector;
        this.updated = updated;
    }

    public WaterMeterDto(WaterMeterEntity waterMeter) {
        this.setId(waterMeter.getId());
        this.setNumber(waterMeter.getNumber());
        this.setTrademark(waterMeter.getTrademark());
        this.setDischargeDate(waterMeter.getCreated());
        this.setComment(waterMeter.getDescription());
        this.setSizeId(waterMeter.getSize().getId());
        this.setSector(waterMeter.getSector());
        this.setUpdated(waterMeter.getUpdated());
    }
}
