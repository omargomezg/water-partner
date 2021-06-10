package com.hardnets.coop.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResumeConsumptionDetailDto {
    private String numberWaterMeter;
    private String rut;
    private String fullName;
    private Long lastRecord;
    private Long actualRecord;
    private Long amountToPaid;
    private Long consumptionId;

    public ResumeConsumptionDetailDto(String numberWaterMeter, String rut, String clientCode, String names, String middleName, String lastName,
                                      String businessName, Long actualRecord, Long consumptionId) {
        this.numberWaterMeter = numberWaterMeter;
        this.rut = rut;
        this.actualRecord = actualRecord;
        this.consumptionId = consumptionId;
        if (clientCode.equals("PARTNER")) {
            setFullName(String.format("%s %s %s", names, middleName == null ? "" : middleName, lastName));
        } else {
            setFullName(businessName);
        }
    }
}
