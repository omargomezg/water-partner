package com.hardnets.coop.model.constant;

/**
 * Es la capacidad definida en milímetros, la cual comunnmente va representada en medidas de 13mm, 19mm, 25mm, 38mm
 * o superiores.
 *
 * @author Omar Gómez
 */
public enum DiameterEnum {
    THIRTEEN(13),
    NINETEEN(19),
    TWENTY_FIVE(25),
    THIRTY_EIGHT(38);

    private final int levelCode;

    private DiameterEnum(int levelCode) {
        this.levelCode = levelCode;
    }
}
