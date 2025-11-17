package com.hardnets.coop.model.dto.request.userSubsidyRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WaterMeter {
    @NotNull
    private Long id;
}
