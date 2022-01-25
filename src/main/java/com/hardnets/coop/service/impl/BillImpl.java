package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.ClientDocuments;
import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.BillDetailRepository;
import com.hardnets.coop.repository.BillRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.SaleDocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Tareas relacionadas a la gestión de boletas de servicios
 */
@AllArgsConstructor
@Log4j2
@Service
public class BillImpl implements SaleDocumentService<BillEntity> {

    private final PeriodRepository periodRepository;
    private final ConsumptionRepository consumptionRepository;
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillDetailService billDetailService;

    @Override
    public BillEntity getById(Long id) {
        return null;
    }

    @Override
    public List<ClientDocuments> getByRut(String rut) {
        var bills = billRepository.getAllByClient_Rut(rut);
        var documents = new ArrayList<ClientDocuments>();
        bills.forEach(bill -> documents.add(ClientDocuments.builder()
                        .id(bill.getId().intValue())
                        .emmit(bill.getDateOfEmission())
                        .amount(bill.getDetail().stream().mapToInt(item -> item.getTotalAmount()).sum())
                        .status(bill.getStatus().name()).build()
                )
        );
        return documents;
    }

    @Override
    public void createByClient(String rut) {

    }

    @Async
    @Transactional
    @Override
    public void createAllInPeriod(long periodId) {
        PeriodEntity period = periodRepository.getById(periodId);
        List<ConsumptionEntity> consumptions = consumptionRepository.findAllByPeriod(period);
        for (ConsumptionEntity consumption : consumptions) {
            if (consumption.getWaterMeter().getClient() != null) {
                BillEntity bill = new BillEntity();
                bill.setClient(consumption.getWaterMeter().getClient());
                bill.setStatus(SalesDocumentStatusEnum.OUTSTANDING);
                bill.setWaterMeter(consumption.getWaterMeter());
                BillEntity dbBill = billRepository.save(bill);
                List<BillDetailEntity> detail = billDetailService.getDetail(consumption, dbBill.getId());
                billDetailRepository.saveAll(detail);
                billRepository.save(bill);
            }
        }
        period.setBillsCreated(true);
        periodRepository.save(period);
    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void sendToClient(Long id) {

    }
}
