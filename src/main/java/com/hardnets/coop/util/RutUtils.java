package com.hardnets.coop.util;

public class RutUtils {

    public static String getFormated(String rut) {
        String fRut[] = new String[5];

        if (String.valueOf(rut).length() == 9) {
            fRut[0] = String.valueOf(rut).substring(0, 2);
            fRut[1] = String.valueOf(rut).substring(2, 5);
            fRut[2] = String.valueOf(rut).substring(5, 8);
            fRut[3] = String.valueOf(rut).substring(8, 9);
        }

        if (String.valueOf(rut).length() == 8) {
            fRut[0] = String.valueOf(rut).substring(0, 1);
            fRut[1] = String.valueOf(rut).substring(1, 4);
            fRut[2] = String.valueOf(rut).substring(4, 7);
            fRut[3] = String.valueOf(rut).substring(7, 8);
        }
        return String.valueOf(fRut[0] + "." + fRut[1] + "." + fRut[2] + "-" + fRut[3]);
    }
}
