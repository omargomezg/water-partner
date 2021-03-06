package com.hardnets.coop.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConsumptionClientDetailDto {

    private Date endDate;
    private Integer reading;
    private Long amount;
    private Date readingDate;

    public ConsumptionClientDetailDto(Date readingDate, Date endDate, Integer reading) {
        this.readingDate = readingDate;
        this.endDate = endDate;
        this.reading = reading;
    }
}
