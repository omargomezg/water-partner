package com.hardnets.coop.model.constant;

public enum DiameterEnum {
    THIRTEEN("13"),
    NINETEEN("19"),
    TWENTY_FIVE("25");

    public final String label;

    private DiameterEnum(String label) {
        this.label = label;
    }

    public static DiameterEnum getByLabel(String label){
        for(DiameterEnum e : DiameterEnum.values()){
            if(e.label.equals(label)) return e;
        }
        return null;
    }
}
