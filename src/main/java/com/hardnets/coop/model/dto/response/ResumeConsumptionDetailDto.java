package com.hardnets.coop.model.dto.response;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResumeConsumptionDetailDto {
    private String numberWaterMeter;
    private String rut;
    private String names;
    private String middleName;
    private String lastName;
    private String fullName;
    private ClientTypeEnum clientType;
    private Long lastRecord;
    private Long actualRecord;
    private Long amountToPaid;
    private Long consumptionId;
    private List<DetailItemDto> detail = new ArrayList<>();

    public ResumeConsumptionDetailDto(String numberWaterMeter, String rut, String clientCode, String names, String middleName, String lastName,
                                      String businessName, Long actualRecord, Long consumptionId) {
        this.numberWaterMeter = numberWaterMeter;
        this.rut = rut;
        this.actualRecord = actualRecord;
        this.consumptionId = consumptionId;
        this.names = names;
        this.middleName = middleName;
        this.lastName = lastName;
        clientType = ClientTypeEnum.valueOf(clientCode);
        this.fullName = clientCode.equals(ClientTypeEnum.PRIVATE) ? businessName : String.format("%s %s %s", names, middleName == null ? "" : middleName, lastName);
    }
}
