package com.hardnets.coop.model.constant;

    public enum ClientTypeEnum {
    RESIDENT_PARTNER("RESIDENT_PARTNER", "Es socio residente"),
    NO_RESIDENT_PARTNER("NO_RESIDENT_PARTNER", "Es socio No residente"),
    PUBLIC("PUBLIC", "Publico"),
    PRIVATE("PRIVATE", "Privado");

    public final String label;
    public final String spanish;

    ClientTypeEnum(String label, String spanish) {
        this.label = label;
        this.spanish = spanish;
    }

    public static boolean isPartner(ClientTypeEnum clientType) {
        return clientType.equals(ClientTypeEnum.NO_RESIDENT_PARTNER) || clientType.equals(ClientTypeEnum.RESIDENT_PARTNER);
    }
}
