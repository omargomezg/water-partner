package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.InvoiceEntity;
import com.hardnets.coop.service.SaleDocumentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Tareas relacionadas a la gestión de facturas
 */
@Service
public class InvoiceImpl implements SaleDocumentService<InvoiceEntity> {

    @Override
    public InvoiceEntity getById(Long id) {
        return null;
    }

    @Override
    public InvoiceEntity getByRut(String rut) {
        return null;
    }

    @Override
    public List<PendingPaymentDto> getAlByStatusAndRut(SalesDocumentStatusEnum status, String rut) {
        return null;
    }

    @Override
    public void createByClient(String rut) {

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
