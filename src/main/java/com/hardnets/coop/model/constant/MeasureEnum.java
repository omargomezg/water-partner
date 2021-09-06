package com.hardnets.coop.model.constant;

public enum MeasureEnum {
    THIRTEEN("13"),
    NINETEEN("19"),
    TWENTY_FIVE("25");

    public final String label;

    private MeasureEnum(String label) {
        this.label = label;
    }
}
