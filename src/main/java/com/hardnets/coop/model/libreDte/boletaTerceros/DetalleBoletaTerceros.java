package com.hardnets.coop.model.libreDte.boletaTerceros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleBoletaTerceros {

    /**
     * Descripción del item a cobrar
     */
    @JsonProperty(value = "NmbItem")
    private String description;

    /**
     * Cantidad
     */
    @JsonProperty(value = "QtyItem")
    private Long quantity;

    /**
     * Valor item
     */
    @JsonProperty(value = "PrcItem")
    private Long unitAmount;

}
