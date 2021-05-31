package com.hardnets.coop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReadingsDto {
    private Long id;
    private Long consumption;
    private Date readingDate;

    public ReadingsDto(Long id, Long consumption, Date readingDate) {
        setId(id);
        setConsumption(consumption);
        setReadingDate(readingDate);
    }
}
