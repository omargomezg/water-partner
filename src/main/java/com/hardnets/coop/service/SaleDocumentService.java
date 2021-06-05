package com.hardnets.coop.service;

public interface SaleDocumentService<T> {

    T getById(Long id);

    void createByClient(String rut);

    void createAllInPeriod(long periodId);

    void get(Long id);

    void sendToClient(Long id);
}
