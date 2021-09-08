package com.hardnets.coop.model.dto.response;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PeriodDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private String status;

    public PeriodDto(Long id, Date startDate, Date endDate, PeriodStatusEnum status) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status.label;
    }
}
