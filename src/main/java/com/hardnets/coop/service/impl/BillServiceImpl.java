package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillDto;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;
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
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Tareas relacionadas a la gestión de boletas de servicios
 */
@AllArgsConstructor
@Log4j2
@Service
public class BillServiceImpl implements SaleDocumentService<BillEntity> {

    private final PeriodRepository periodRepository;
    private final ConsumptionRepository consumptionRepository;
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillDetailService billDetailService;
    private final ConversionService conversionService;

    @Override
    public BillEntity getById(Long id) {
        return null;
    }

    @Override
    public IssuedBillsDto getByRut(String rut, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        IssuedBillsDto issuedBills = new IssuedBillsDto();
        var bills = billRepository.getAllByClient_Rut(rut, pageable);
        issuedBills.setTotalHits(bills.getTotalElements());
        issuedBills.setContents(
                bills.getContent().stream()
                        .map(this::convertToIssuedBillDto)
                        .collect(Collectors.toList())
        );
        return issuedBills;
    }

    @Override
    public IssuedBillsDto getAllByStatusAndRut(SalesDocumentStatusEnum status, String rut, Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        IssuedBillsDto issuedBillsDto = new IssuedBillsDto();
        var bills = billRepository.getAllByStatusAndClient_RutOrderByDateOfEmissionAsc(status, rut, pageable);
        issuedBillsDto.setTotalHits(bills.getTotalElements());
        issuedBillsDto.setContents(
                bills.getContent().stream()
                        .map(this::convertToIssuedBillDto)
                        .collect(Collectors.toList())
        );
        return issuedBillsDto;
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

    private IssuedBillDto convertToIssuedBillDto(BillEntity bill) {
        var dto = conversionService.convert(bill, IssuedBillDto.class);
        if (dto != null) {
            dto.setAmount(getTotalAmount(bill));
        }
        return dto;
    }

    private Long getTotalAmount(BillEntity bill) {
        return billDetailRepository.findAllByBill(bill).stream().mapToLong(BillDetailEntity::getTotalAmount).sum();
    }
}
