package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class AllTariffsDto {
    private Long id;
    private DiameterEnum diameter;
    private Integer flatFee;
    private Float cubicMeter;
    private ClientTypeDTO clientType;
    private Instant lastUpdate;
    private StatusEnum status;
}
