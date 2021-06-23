package com.hardnets.coop.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConsumptionClientDetailDto {

    private Date endDate;
    private Long consumption;
    private Long amount;
    private Date readingDate;
    private Byte currentMonth;

    public ConsumptionClientDetailDto(Date readingDate, Date endDate, Long consumption, Byte currentMonth) {
        this.readingDate = readingDate;
        this.endDate = endDate;
        this.consumption = consumption;
        this.currentMonth = currentMonth;
    }
}
