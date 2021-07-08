package com.hardnets.coop.model.dto.request.userSubsidyRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WaterMeter {
    @NotNull
    private Long id;
}
