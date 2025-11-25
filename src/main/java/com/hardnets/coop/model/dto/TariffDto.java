package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffDto {
    private Long id;
    private Float cubicMeter;
    private Integer flatFee;
    private Long clientType;
    private Integer diameter;

    @Builder.Default
    private String status = StatusEnum.ACTIVE.name();

}
