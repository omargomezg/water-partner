package com.hardnets.coop.model.dto.response;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Boolean billsCreated;
    private Boolean invoicesCreated;
    private PeriodStatusEnum status;
}
