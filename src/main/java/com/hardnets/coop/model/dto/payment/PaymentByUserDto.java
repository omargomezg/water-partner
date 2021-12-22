package com.hardnets.coop.model.dto.payment;

import lombok.Data;

@Data
public class PaymentByUserDto {
    private Long totalHits = 0L;
    private PaymentByUserDetailDto detail;
}
