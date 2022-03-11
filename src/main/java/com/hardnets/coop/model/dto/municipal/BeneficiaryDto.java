package com.hardnets.coop.model.dto.municipal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BeneficiaryDto {
    /**
     * Código: DNI
     */
    private String dni;

    /**
     * Código: DV-RUT
     */
    private String names;

    /**
     * Código: AP. PATERNO
     */
    private String middleName;

    /**
     * Código: AP. MATERNO
     */
    private String lastName;

    /**
     * Código: DIRECCION
     */
    private String address;
}
