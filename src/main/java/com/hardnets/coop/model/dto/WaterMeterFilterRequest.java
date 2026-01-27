package com.hardnets.coop.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WaterMeterFilterRequest extends PageRequest {

    private String serial;
    private Boolean isAssigned;
    private String text;

}
