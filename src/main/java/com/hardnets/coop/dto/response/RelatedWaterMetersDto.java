package com.hardnets.coop.dto.response;

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
    private String millimeter;
    private Date dischargeDate;
    private String sector;
    private String tariff;
    private Short percentage;

    public RelatedWaterMetersDto(Long id, String number, String millimeter, Date dischargeDate, String sector, Short percentage) {
        this.id = id;
        this.number = number;
        this.millimeter = millimeter;
        this.dischargeDate = dischargeDate;
        this.sector = sector;
        this.percentage = percentage;
    }
}
