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
                                     Integer reading, String firstName, String middleName, String lastName,
                                     String businessName) {
        this.id = id;
        this.serial = serial;
        this.diameter = diameter.name();
        this.dischargeDate = dischargeDate;
        this.sector = sector;
        this.reading = reading;
        this.client = getFullName(firstName, middleName, lastName, businessName);
    }

    private String getFullName(String firstName, String middleName, String lastName, String businessName) {
        if (businessName.isEmpty())
            return String.format("%s %s %s",
                    firstName,
                    middleName != null ? middleName : "", lastName);
        else
            return businessName;
    }
}
