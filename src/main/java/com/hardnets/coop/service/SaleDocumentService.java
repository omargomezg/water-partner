package com.hardnets.coop.service;

public interface SaleDocumentService<T> {

    T getById(Long id);

    Object getByRut(String rut);

    void createByClient(String rut);

    void createAllInPeriod(long periodId);

    void get(Long id);

    void sendToClient(Long id);
}
