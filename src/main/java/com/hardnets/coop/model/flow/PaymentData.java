package com.hardnets.coop.model.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentData {

    /**
     * La fecha de pago
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    /**
     * El medio de pago utilizado
     */
    private String media;

    /**
     * La fecha de conversión de la moneda
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
     * El costo del servicio
     */
    private Float taxes;

    /**
     * El saldo a depositar
     */
    private Float balance;

    /**
     * La fecha de transferencia de los fondos a su cuenta bancaria.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transferDate;
}
