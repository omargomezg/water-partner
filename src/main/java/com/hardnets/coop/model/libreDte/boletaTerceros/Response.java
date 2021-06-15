package com.hardnets.coop.model.libreDte.boletaTerceros;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Response {
    //{"emisor":73741900,"receptor":14081226,"dte":33,"codigo":"628dd16732ffe3f303cb23be78084465"}
    @JsonProperty(value = "emisor")
    private String companyRut;

    @JsonProperty(value = "receptor")
    private String clientRut;

    @JsonProperty(value = "dte")
    private Integer documentType;

    @JsonProperty(value = "codigo")
    private String hash;
}
