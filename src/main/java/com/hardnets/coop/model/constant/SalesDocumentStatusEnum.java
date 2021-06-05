package com.hardnets.coop.model.constant;

public enum SalesDocumentStatusEnum {
    /**
     * Cuando el documento no ha sido enviado al SII
     */
    DRAFT,
    /**
     * Pendiente de pago, ha sido recibido por el SII
     */
    OUTSTANDING,
    /**
     * Pagada
     */
    PAID,
    /**
     * Rechazada
     */
    REJECTED,
    /**
     * Cancelada
     */
    CANCELED
}
