package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterMeterDto {
    private Long id;
    private Integer serial;
    private String trademark;
    private DiameterEnum diameter;
    private String comment;
    private String sector;
    private Date updated;
    private String dni;

    /**
     * Date when is related to
     */
    private Date dischargeDate;

    public WaterMeterDto(Long id, Integer serial, String trademark, DiameterEnum diameter, String comment,
                         String sector,
                         Date updated) {
        this.id = id;
        this.serial = serial;
        this.trademark = trademark;
        this.diameter = diameter;
        this.comment = comment;
        this.sector = sector;
        this.updated = updated;
    }

    public WaterMeterDto(WaterMeterEntity waterMeter) {
        this.setId(waterMeter.getId());
        this.setSerial(waterMeter.getSerial());
        this.setTrademark(waterMeter.getTrademark());
        this.setDischargeDate(waterMeter.getCreated());
        this.setComment(waterMeter.getDescription());
        this.diameter = waterMeter.getDiameter();
        this.setSector(waterMeter.getSector());
        this.setUpdated(waterMeter.getUpdated());
    }
}
