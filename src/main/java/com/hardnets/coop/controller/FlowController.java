package com.hardnets.coop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.flow.PaymentOrderResponse;
import com.hardnets.coop.service.FlowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@AllArgsConstructor
@RestController
public class FlowController {

    private final FlowService flowService;

    @PostMapping("/v1/flow/payment-order")
    public PaymentOrderResponse sendPaymentOrder(@RequestBody PendingPaymentDto pendingPayment)
            throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        return flowService.sendPaymentOrder(pendingPayment);
    }

}
