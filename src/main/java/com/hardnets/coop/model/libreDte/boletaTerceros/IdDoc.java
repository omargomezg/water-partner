package com.hardnets.coop.model.libreDte.boletaTerceros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IdDoc {

    @JsonProperty(value = "TipoDTE")
    private Integer TipoDTE;

    @JsonProperty(value = "Folio")
    private Integer folio;
}
