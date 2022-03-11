package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;
import com.hardnets.coop.model.entity.InvoiceEntity;
import com.hardnets.coop.service.SaleDocumentService;
import org.springframework.stereotype.Service;

/**
 * Tareas relacionadas a la gestión de facturas
 */
@Service
public class InvoiceServiceImpl implements SaleDocumentService<InvoiceEntity> {

    @Override
    public InvoiceEntity getById(Long id) {
        return null;
    }

    @Override
    public IssuedBillsDto getByDni(String dni, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public IssuedBillsDto getAllByStatusAndDni(SalesDocumentStatusEnum status, String dni, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public void createByClient(String dni) {

    }

    @Override
    public void createAllInPeriod(long periodId) {

    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void sendToClient(Long id) {

    }
}
