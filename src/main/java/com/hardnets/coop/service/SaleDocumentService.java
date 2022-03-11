package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;

public interface SaleDocumentService<T> {

    T getById(Long id);

    IssuedBillsDto getByDni(String dni, Integer pageIndex, Integer pageSize);

    IssuedBillsDto getAllByStatusAndDni(SalesDocumentStatusEnum status, String dni, Integer pageIndex, Integer pageSize);

    void createByClient(String dni);

    void createAllInPeriod(long periodId);

    void get(Long id);

    void sendToClient(Long id);
}
