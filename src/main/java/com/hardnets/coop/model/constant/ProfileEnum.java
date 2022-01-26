package com.hardnets.coop.model.constant;

public enum ProfileEnum {
    /**
     * Full access and permission
     */
    ADMINISTRATOR("Administrator"),
    FINANCE("Finance"),
    RAISING("Raising"),
    BILLING("Billing"),
    KAL_EL("Kal-El");

    public final String label;

    private ProfileEnum(String label) {
        this.label = label;
    }
}
