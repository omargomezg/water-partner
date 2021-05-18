package com.hardnets.coop.dto.response;

import com.hardnets.coop.dto.WaterMeterDto;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SubsidyDto {
    @Nullable
    private Long id;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endingDate;
    @NotNull
    private Short percentage;
    private String observation;
    private WaterMeterDto waterMeter;

    public SubsidyDto(Date startDate, Date endingDate, Short percentage, String observation) {
        this.startDate = startDate;
        this.endingDate = endingDate;
        this.percentage = percentage;
        this.observation = observation;
    }
}
