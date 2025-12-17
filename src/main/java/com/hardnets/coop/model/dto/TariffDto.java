package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    private Long id;
    private Float cubicMeter;
    private Integer flatFee;
    private Instant lastUpdate;

    @NonNull
    private ClientTypeDTO clientType;
    private DiameterEnum diameter;

    @Builder.Default
    private String status = StatusEnum.ACTIVE.name();

}
