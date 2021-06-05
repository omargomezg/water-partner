package com.hardnets.coop.model.constant;

/**
 * Enumera los tipos de cálculos que están disponibles
 */
public enum CalculationTypeEnum {
    /**
     * Expresa el calculo de un consumo normal de agua
     */
    CONSUMPTION,
    /**
     * Cuando un usuario tiene relacionado un subsidio
     */
    SUBSIDY,
    /**
     * Calculo por cobro de excedente (sobre consumo)
     */
    EXCESS;
}
