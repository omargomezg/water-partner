package com.hardnets.coop.model.constant;

public enum ProfileEnum {
    ADMINISTRATOR("Administrator"),
    FINANCE("Finance"),
    RAISING("Raising"),
    BILLING("Billing");

    public final String label;

    private ProfileEnum(String label) {
        this.label = label;
    }
}
