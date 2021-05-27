package com.hardnets.coop.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingPaymentDto {
    private String documentNumber;
    private Long amount;
}
