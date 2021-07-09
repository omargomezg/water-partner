package com.hardnets.coop.model.dto.request;

import com.hardnets.coop.model.dto.request.userSubsidyRequest.Decree;
import com.hardnets.coop.model.dto.request.userSubsidyRequest.Subsidy;
import com.hardnets.coop.model.dto.request.userSubsidyRequest.WaterMeter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserSubsidyRequest {

    @NotNull
    private WaterMeter waterMeter;

    @NotNull
    private Decree decree;

    @NotNull
    private Subsidy subsidy;

    private String observation = "";
}
