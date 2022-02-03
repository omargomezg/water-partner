package com.hardnets.coop.model.constant;

/**
 * Enumera los tipos de cálculos que están disponibles
 */
public enum CalculationTypeEnum {
    /**
     * Expresa el calculo de un consumo normal de agua
     */
    CONSUMPTION("Consumo"),
    /**
     * Cuando un usuario tiene relacionado un subsidio
     */
    SUBSIDY("Subsidio"),
    /**
     * Calculo por cobro de excedente (sobre consumo)
     */
    EXCESS("Sobreconsumo");

    public final String spanish;

    CalculationTypeEnum(String spanish) {
        this.spanish = spanish;
    }

}
