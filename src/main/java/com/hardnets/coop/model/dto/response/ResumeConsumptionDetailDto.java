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
    /**
     * Serial number of water meter
     */
    private String serial;
    private String rut;
    private String names;
    private String middleName;
    private String lastName;
    private String fullName;
    private String clientType;
    private Integer lastRecord;
    private Integer actualRecord;
    private Long amountToPaid;
    private Long consumptionId;
    private List<DetailItemDto> detail = new ArrayList<>();

    public ResumeConsumptionDetailDto(String serial, String rut, ClientTypeEnum clientType, String names,
                                      String middleName, String lastName,
                                      String businessName, Integer actualRecord, Long consumptionId) {
        this.serial = serial;
        this.rut = rut;
        this.actualRecord = actualRecord;
        this.consumptionId = consumptionId;
        this.names = names;
        this.middleName = middleName;
        this.lastName = lastName;
        this.clientType = clientType.label;
        this.fullName = clientType.equals(ClientTypeEnum.PRIVATE) ? businessName : String.format("%s %s %s", names, middleName == null ? "" : middleName, lastName);
    }
}
