package com.hardnets.coop.model.libreDte.boletaTerceros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Encabezado {
    @JsonProperty(value = "IdDoc")
    private IdDoc documento = new IdDoc();

    @JsonProperty(value = "Emisor")
    private Emisor company = new Emisor();

    @JsonProperty(value = "Receptor")
    private Receptor client = new Receptor();
}
