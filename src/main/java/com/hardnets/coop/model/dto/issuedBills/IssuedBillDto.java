package com.hardnets.coop.model.dto.issuedBills;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class IssuedBillDto {

    private String documentNumber;

    private Date dateOfEmission;

    private String documentType;

    private String status;

    @Builder.Default
    private Long amount = 0L;
}
