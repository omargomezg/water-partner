package com.hardnets.coop.model.libreDte.boletaTerceros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Receptor {

    @JsonProperty(value = "RUTRecep")
    private String rut;

    /**
     * Razón social
     */
    @JsonProperty(value = "RznSocRecep")
    private String businessName = "";

    /**
     * Giro
     */
    @JsonProperty(value = "GiroRecep")
    private String businessActivity = "";

    /**
     * Dirección
     */
    @JsonProperty(value = "DirRecep")
    private String address = "";

    /**
     * Comuna del cliente
     */
    @JsonProperty(value = "CmnaRecep")
    private String commune = "";
}
