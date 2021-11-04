package com.hardnets.coop.model.constant;

public enum PeriodStatusEnum {
    CLOSED("Closed"),
    ACTIVE("Active");

    public final String label;

    PeriodStatusEnum(String label) {
        this.label = label;
    }
}
