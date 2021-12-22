package com.hardnets.coop.model.dto.payment;

import lombok.Data;

import java.util.Date;

@Data
public class PaymentByUserDetailDto {
    private Long documentNumber;
    private Date emmit;
    private Date expiration;
}
