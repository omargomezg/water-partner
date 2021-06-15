package com.hardnets.coop.service;

public interface SaleDocumentService<T> {

    T getById(Long id);

    void createByClient(String rut);

    void createAllInPeriod(long periodId);

    void get(Long id);

    void sendToClient(Long id);

    /**
     * Emite un documento temporal en SII
     */
    void emitDocumentTaxElectronic();

    /**
     * Genera un Documento en SII, antes denominado temporal
     */
    void generateDte();

    /**
     * Consultar el estado del envío en SII del documento, puede estar aceptado, rechazado o
     * aceptado con reparos
     */
    void updateStatusDte(String trackId);

    /**
     * Genera el documento Pdf del documento electrónico
     */
    void generatePdf();
}
