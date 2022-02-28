package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;

public interface SaleDocumentService<T> {

    T getById(Long id);

    IssuedBillsDto getByRut(String rut, Integer pageIndex, Integer pageSize);

    IssuedBillsDto getAllByStatusAndRut(SalesDocumentStatusEnum status, String rut, Integer pageIndex, Integer pageSize);

    void createByClient(String rut);

    void createAllInPeriod(long periodId);

    void get(Long id);

    void sendToClient(Long id);
}
