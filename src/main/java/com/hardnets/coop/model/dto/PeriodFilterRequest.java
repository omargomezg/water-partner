package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PeriodFilterRequest extends PageRequest {
    private PeriodStatusEnum status;
}
