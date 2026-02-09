package com.hardnets.coop.model.dto.response;

import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.dto.views.AppViews;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubsidyDto {

    @Nullable
    @JsonView(AppViews.Internal.class)
    private Long id;

    @NotNull
    @JsonView(AppViews.Internal.class)
    private Date startDate;

    @NotNull
    @JsonView(AppViews.Internal.class)
    private Date endingDate;

    @NotNull
    @JsonView(AppViews.Internal.class)
    private Short percentage;

    @JsonView(AppViews.Internal.class)
    private String observation;

    @JsonView(AppViews.Internal.class)
    private Integer numberOfDecree;

    @JsonView(AppViews.Internal.class)
    private Date approvedDateOfDecree;

    @JsonView(AppViews.Internal.class)
    private WaterMeterDTO waterMeter;

    public SubsidyDto(Long id, Date startDate, Date endingDate, Short percentage, String observation) {
        this.id = id;
        this.startDate = startDate;
        this.endingDate = endingDate;
        this.percentage = percentage;
        this.observation = observation;
    }
}
