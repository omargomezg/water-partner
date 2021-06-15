package com.hardnets.coop.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.PaymentEntity;
import com.hardnets.coop.model.flow.PaymentOrderResponse;
import com.hardnets.coop.model.flow.PaymentOrderStatusResponse;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Service que implementa métodos para pago a través de flow
 * https://www.flow.cl/docs/api.html
 *
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
public interface PaymentService {

    /**
     * Crea una orden de pago en Flow y recibe como respuesta la url para redirigir el browser del pagador y el token
     * que identifica la transacción
     */
    PaymentOrderResponse sendPaymentOrder(PendingPaymentDto paymentOrder) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException;

    /**
     * Confirmación del pago por parte de flow
     *
     * @param token  token que ha sido confirmado
     * @param status
     * @return Url del front la cual mostrara el estado del pago al cliente
     */
    String confirmationPaymentOrder(String token, Byte status);

    PaymentOrderStatusResponse findFlowPaymentStatusByToken(String token) throws InvalidKeyException, NoSuchAlgorithmException, JsonProcessingException;

    void save(String token, Long flowOrder);

    PaymentEntity findByToken(String token);

    PaymentEntity update(String token);

}
