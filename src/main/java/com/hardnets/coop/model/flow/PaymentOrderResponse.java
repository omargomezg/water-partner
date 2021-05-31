package com.hardnets.coop.model.flow;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentOrderResponse {
    private String url;
    private String token;
    private Long flowOrder;
}
