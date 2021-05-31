package com.hardnets.coop.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.flow.PaymentOrderResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Service que implementa métodos para pago a través de flow
 * https://www.flow.cl/docs/api.html
 */
public interface FlowService {

    /**
     * Crea una orden de pago en Flow y recibe como respuesta la url para redirigir el browser del pagador y el token
     * que identifica la transacción
     */
    PaymentOrderResponse sendPaymentOrder(PendingPaymentDto paymentOrder) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException;
}
