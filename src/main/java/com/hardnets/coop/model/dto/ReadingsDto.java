package com.hardnets.coop.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReadingsDto {
    private Long id;
    private Integer reading;
    private Date readingDate;

    public ReadingsDto(Long id, Integer reading, Date readingDate) {
        setId(id);
        setReading(reading);
        setReadingDate(readingDate);
    }
}
