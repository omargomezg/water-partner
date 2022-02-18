package com.hardnets.coop.model.constant;

public enum ProfileEnum {
    /**
     * Full access and permission
     */
    ADMINISTRATOR("Administrator"),
    /**
     * Finanzas
     */
    FINANCE("Finance"),
    /**
     * Recaudador
     */
    RAISING("Raising"),
    /**
     * Tesorería
     */
    BILLING("Billing"),
    /**
     * Super administrator
     */
    KAL_EL("Kal-El");

    public final String label;

    private ProfileEnum(String label) {
        this.label = label;
    }
}
