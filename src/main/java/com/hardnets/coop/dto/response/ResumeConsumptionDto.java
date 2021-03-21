package com.hardnets.coop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Return an resume of consumption for a period
 */
@Getter
@Setter
@NoArgsConstructor
public class ResumeConsumptionDto {
    private Date startDate;
    private Date endDate;
    private String status;
    private List<ResumeConsumptionDetailDto> detail;
}
