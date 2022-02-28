package com.hardnets.coop.model.constant;

public enum PeriodStatusEnum {
    /**
     * Ultimo estado, ya no se puede ingresar mas lecturas
     */
    CLOSED("Closed"),
    /**
     * Indica que el periodo esta actualmente en uso y las nuevas lecturas se relacionan a este periodo
     */
    ACTIVE("Active"),
    /**
     * Indica que el periodo se encuentra creado pero sin ser usado aún
     */
    PREPARED("Prepared");

    public final String label;

    PeriodStatusEnum(String label) {
        this.label = label;
    }
}
