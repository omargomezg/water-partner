package com.hardnets.coop.model.constant;

public enum PeriodStatusEnum {
    CLOSED("Cerrado"),
    ACTIVE("Abierto");

    public final String label;

    private PeriodStatusEnum(String label) {
        this.label = label;
    }
}
