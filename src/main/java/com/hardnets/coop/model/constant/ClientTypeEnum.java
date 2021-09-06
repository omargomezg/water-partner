package com.hardnets.coop.model.constant;

public enum ClientTypeEnum {
    PARTNER("PARTNER"),
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE");

    public final String label;

    private ClientTypeEnum(String label) {
        this.label = label;
    }
}
