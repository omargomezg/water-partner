package com.hardnets.coop.model.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Es la capacidad definida en milímetros, la cual comunmente va representada en medidas de 13mm, 19mm, 25mm, 38mm
 * o superiores.
 *
 * @author Omar Gómez
 */
public enum DiameterEnum {
    THIRTEEN(13),
    NINETEEN(19),
    TWENTY_FIVE(25),
    THIRTY_EIGHT(38);

    private final int diameter;

    private static Map<Integer, DiameterEnum> diameterEnumMap = new HashMap<>();

    public int getValue(){
        return diameter;
    }

    static {
        for (DiameterEnum diameterEnum : DiameterEnum.values()) {
            diameterEnumMap.put(
                    diameterEnum.diameter,
                    diameterEnum
            );
        }
    }

    DiameterEnum(int diameter) {
        this.diameter = diameter;
    }

    // TODO Obtener el numero del diametro
    public static DiameterEnum valueOfLabel(int label) {
        return diameterEnumMap.get(label);
    }

    public static DiameterEnum castIntToEnum(int diameter) {
        return diameterEnumMap.get(diameter);
    }

}
