package com.hardnets.coop.model.dto.municipal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ReportDto {
    /**
     * Codigo de comuna
     * Código: CODCOM
     */
    private Integer codeCommune;

    /**
     * Nombre de la comuna
     * Código: ORIGEN
     */
    private String nameCommune;

    /**
     * Jefe de familia
     */
    private BeneficiaryDto beneficiary;

    /**
     * Decreto
     */
    private DecreeDto decree;

    /**
     * Puntaje
     * Código: PUNTAJE
     */
    private Integer score;

    /**
     * Fecha de la encuesta
     * Código: FEC-ENC
     */
    private Date pollDate;

    /**
     * Número único, debe contener un numero más digito verificador
     * Código: NUMUNICO
     * Código: DV-NUMUNICO
     */
    private Long uniqueNumber;

    /**
     * Numero de viviendas abastecidas por el mismo arranque
     * Código: NUMVIVTOT
     */
    private Integer numVivTot;

    /**
     * m3 consumidos
     * Código: CONSUMO
     */
    private Integer consumption;

    /**
     * Monto subsidio cobrado municipio
     * Código: MONSUBS
     */
    private Integer subsidizedAmount;

    /**
     * Monto subsidio cobrado beneficiario
     * Código: MONSUBS
     */
    private Integer amountChargedBeneficiary;

    /**
     * Número de cuentas adeudadas
     * Código: NUMDEUD
     */
    private Integer numberOfAccountsOwed;

    /**
     * Monto de la deuda
     * Código: MONDEUD
     */
    private Integer debtAmount;

    /**
     * Causal de no efectividad del subsidio
     * Código: OBSERVACION
     */
    private String observation;
}
