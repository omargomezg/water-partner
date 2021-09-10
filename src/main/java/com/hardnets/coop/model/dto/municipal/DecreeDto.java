package com.hardnets.coop.model.dto.municipal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class DecreeDto {
    private Long number;
    private Date approvalDate;
}
