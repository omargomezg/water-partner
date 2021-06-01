package com.hardnets.coop.model.flow;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentData {

    /**
     * La fecha de pago
     */
    private Date date;

    /**
     * El medio de pago utilizado
     */
    private String media;

    /**
     * La fecha de conversión de la moneda
     */
    private Date conversionDate;

    /**
     * La tasa de conversión.
     */
    private Float conversionRate;

    /**
     * El monto pagado
     */
    private Float amount;

    /**
     * La moneda con que se pagó
     */
    private String currency;

    /**
     * El costo del servicio
     */
    private Float fee;

    /**
     * El saldo a depositar
     */
    private Float balance;

    /**
     * La fecha de transferencia de los fondos a su cuenta bancaria.
     */
    private Date transferDate;
}
