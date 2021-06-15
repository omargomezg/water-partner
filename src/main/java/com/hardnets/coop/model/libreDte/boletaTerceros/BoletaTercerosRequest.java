package com.hardnets.coop.model.libreDte.boletaTerceros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Getter
@Setter
public class BoletaTercerosRequest {

    @JsonProperty(value = "Encabezado")
    private Encabezado encabezado = new Encabezado();

    @JsonProperty(value = "Detalle")
    private List<DetalleBoletaTerceros> detalle = new ArrayList<>();
}
