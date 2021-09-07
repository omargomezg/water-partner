package com.hardnets.coop.model.dto.response;

import com.hardnets.coop.model.constant.DiameterEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RelatedWaterMetersDto {
    private Long id;
    private String number;
    private DiameterEnum diameter;
    private Date dischargeDate;
    private String sector;
    private Integer flatFee;
    private Float cubicMeter;
    private Short percentage;

    public RelatedWaterMetersDto(Long id, String number, DiameterEnum diameter, Date dischargeDate, String sector,
                                 Integer flatFee, Float cubicMeter, Short percentage) {
        this.id = id;
        this.number = number;
        this.diameter = diameter;
        this.dischargeDate = dischargeDate;
        this.sector = sector;
        this.flatFee = flatFee;
        this.cubicMeter = cubicMeter;
        this.percentage = percentage;
    }
}
