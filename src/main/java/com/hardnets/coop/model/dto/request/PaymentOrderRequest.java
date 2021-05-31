package com.hardnets.coop.model.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PaymentOrderRequest {
    private Set<Long> documentsId;
}
