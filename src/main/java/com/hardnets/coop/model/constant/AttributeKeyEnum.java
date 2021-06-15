package com.hardnets.coop.model.constant;

public enum AttributeKeyEnum {
    LIBRE_DTE_URL("libreDteUrl"),
    LIBRE_DTE_HASH("libreDteHash"),
    FLOW_URL("flowUrl"),
    FLOW_KEY("flowKey"),
    FLOW_TOKEN("flowToken");

    private final String key;

    AttributeKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
