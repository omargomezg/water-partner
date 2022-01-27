package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;

import java.util.List;

public interface SaleDocumentService<T> {

    T getById(Long id);

    Object getByRut(String rut);

    List<PendingPaymentDto> getAlByStatusAndRut(SalesDocumentStatusEnum status, String rut);

    void createByClient(String rut);

    void createAllInPeriod(long periodId);

    void get(Long id);

    void sendToClient(Long id);
}
