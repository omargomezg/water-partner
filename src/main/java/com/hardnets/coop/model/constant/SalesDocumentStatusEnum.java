package com.hardnets.coop.model.constant;

import java.util.HashMap;
import java.util.Map;

public enum SalesDocumentStatusEnum {
    /**
     * Cuando el documento no ha sido enviado al SII
     */
    DRAFT(0),
    /**
     * Pendiente de pago, ha sido recibido por el SII
     */
    OUTSTANDING(1),
    /**
     * Pagada
     */
    PAID(2),
    /**
     * Rechazada
     */
    REJECTED(3),
    /**
     * Cancelada
     */
    CANCELED(4);

    private static Map<Integer, SalesDocumentStatusEnum> salesDocumentStatusEnumMap = new HashMap<>();

    static {
        for (SalesDocumentStatusEnum salesDocumentStatusEnum : SalesDocumentStatusEnum.values()) {
            salesDocumentStatusEnumMap.put(
                    salesDocumentStatusEnum.status,
                    salesDocumentStatusEnum
            );
        }
    }

    private final int status;

    SalesDocumentStatusEnum(int status) {
        this.status = status;
    }

    public static SalesDocumentStatusEnum castIntToEnum(int status) {
        return salesDocumentStatusEnumMap.get(status);
    }
}
