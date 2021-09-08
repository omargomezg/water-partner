package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.CalculationTypeEnum;
import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.ItemEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.repository.BillRepository;
import com.hardnets.coop.repository.ItemRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.service.ItemCalculationService;
import com.hardnets.coop.service.SaleDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillDetailService implements SaleDetailService<BillDetailEntity> {

    private final ItemRepository itemRepository;
    private final BillRepository billRepository;
    private final ItemCalculationService itemCalculationService;
    private final SubsidyRepository subsidyRepository;

    @Override
    public List<BillDetailEntity> getDetail(ConsumptionEntity consumption, Long billId) {
        Optional<BillEntity> bill = billRepository.findById(billId == null ? -1 : billId);
        List<BillDetailEntity> billDetail = new ArrayList<>();
        List<ItemEntity> items = itemRepository.findAllByIsActive(true);
        for (ItemEntity item : items) {
            BillDetailEntity detail = new BillDetailEntity();
            detail.setConcept(item.getExcerpt());
            bill.ifPresent(detail::setBill);
            if (Boolean.TRUE.equals(item.getIsFixedAmount())) {
                detail.setTotalAmount(item.getAmount());
            } else {
                if (item.getMethodOfCalculating().equals(CalculationTypeEnum.CONSUMPTION)) {
                    detail.setConsumption(consumption.getReading());
                }
                detail.setBaseAmount(item.getAmount());
                detail.setTotalAmount(
                        getTotalAmount(item.getMethodOfCalculating(), consumption)
                );
            }
            billDetail.add(detail);
        }
        Optional<SubsidyEntity> subsidy =
                subsidyRepository.findAllByWaterMeterAndIsActiveAndEndingDateAfter(consumption.getWaterMeter(),
                        true, new Date());
        if (subsidy.isPresent()) {
            BillDetailEntity detailSubsidy = getSubsidizedAmount(subsidy.get(), consumption);
            bill.ifPresent(detailSubsidy::setBill);
            billDetail.add(detailSubsidy);
        }
        return billDetail;
    }

    private Integer getTotalAmount(CalculationTypeEnum calculationType, ConsumptionEntity consumption) {
        Integer total;
        switch (calculationType) {
            case SUBSIDY:
                total = itemCalculationService.getSubsidyAmount(consumption);
                break;
            case EXCESS:
                total = itemCalculationService.getExcessAmount();
                break;
            case CONSUMPTION:
                total = itemCalculationService.getConsumptionAmount(consumption);
                break;
            default:
                total = 0;
                break;
        }
        return total;
    }

    private BillDetailEntity getSubsidizedAmount(SubsidyEntity subsidy, ConsumptionEntity consumption) {
        Integer total = getTotalAmount(CalculationTypeEnum.SUBSIDY, consumption) * -1;
        BillDetailEntity billDetail = new BillDetailEntity();
        billDetail.setConcept("Subsidio " + subsidy.getPercentage() + "% consumo");
        billDetail.setTotalAmount(total);
        return billDetail;
    }
}
