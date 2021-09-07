package com.hardnets.coop.model.constant;

public enum StatusEnum {
    NEW("Nuevo"),
    USED("Usado"),
    BAD("Dañado");

    public final String label;

    private StatusEnum(String label) {
        this.label = label;
    }

    public static StatusEnum getByLabel(String label){
        for(StatusEnum e : StatusEnum.values()){
            if(e.label.equals(label)) return e;
        }
        return null;
    }
}
