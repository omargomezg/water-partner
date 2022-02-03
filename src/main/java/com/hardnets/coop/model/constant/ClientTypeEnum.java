package com.hardnets.coop.model.constant;

public enum ClientTypeEnum {
    PARTNER("PARTNER", "Es socio"),
    PUBLIC("PUBLIC", "Publico"),
    PRIVATE("PRIVATE", "Privado");

    public final String label;
    public final String spanish;

    ClientTypeEnum(String label, String spanish) {
        this.label = label;
        this.spanish = spanish;
    }
}
