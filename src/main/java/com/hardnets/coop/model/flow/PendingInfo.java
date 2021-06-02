package com.hardnets.coop.model.flow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Información para un pago pendiente cuando se generó un cupón de pago.
 * Si no existen datos es que no se generó un cupón de pago.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PendingInfo {

    /**
     * El medio de pago utilizado para emitir el cupón de pago
     */
    private String media;

    /**
     * La fecha de emisión del cupón de pago
     */
    private Date date;
}
