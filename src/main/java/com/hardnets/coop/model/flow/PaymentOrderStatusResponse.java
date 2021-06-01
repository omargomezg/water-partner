package com.hardnets.coop.model.flow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentOrderStatusResponse {

    /**
     * El número de la orden de Flow
     */
    private Long flowOrder;

    /**
     * El número de la orden del comercio
     */
    private String commerceOrder;

    /**
     * La fecha de creación de la orden
     */
    private Date requestDate;

    /**
     * El estado de la order, donde:
     * - 1 Pendiente de Pago
     * - 2 Pagada
     * - 3 Rechazada
     * - 4 Anulada
     */
    private Byte status;

    /**
     * El concepto de la orden
     */
    private String subject;

    /**
     * La moneda
     */
    private String currency;

    /**
     * El monto de la orden
     */
    private Long amount;

    /**
     * El email del pagador
     */
    private String payer;

    /**
     * Id de comercio asociado. Solo aplica si usted es comercio integrador.
     */
    private String merchantId;

    /**
     * datos opcionales enviados por el comercio en el request de creación de pago en el parámetro optional en formato JSon
     */
    private String optional;

    /**
     * Información para un pago pendiente cuando se generó un cupón de pago.
     * Si no existen datos es que no se generó un cupón de pago.
     */
    @JsonProperty(value = "pending_info")
    private PendingInfo pendingInfo;

    /**
     * Los datos del pago
     */
    private PaymentData paymentData;
}
