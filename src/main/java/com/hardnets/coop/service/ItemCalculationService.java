package com.hardnets.coop.service;

import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.ItemEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.ItemRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.TariffRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class ItemCalculationService {

    private final ItemRepository itemRepository;
    private final TariffRepository tariffRepository;
    private final SubsidyRepository subsidyRepository;

    /**
     * @return el monto subsidiado
     */
    public Long getSubsidyAmount(ConsumptionEntity consumption) {
        Optional<SubsidyEntity> subsidy =
                subsidyRepository.findAllByWaterMeter_IdAndIsActiveAndEndingDateAfter(consumption.getWaterMeter().getId(), true,
                        new Date());
        if (subsidy.isPresent()) {
            short percentage = subsidy.get().getPercentage();
            Long total = getConsumptionAmount(consumption);
            return (total * percentage) / 100;
        }
        return 0L;
    }

    /**
     * @return el monto a cobrar por exceso de consumo
     */
    public Long getExcessAmount() {
        return 0L;
    }

    /**
     * @return El monto a cobrar por consumo
     */
    public Long getConsumptionAmount(ConsumptionEntity consumption) {
        if (consumption.getWaterMeter().getClient() != null) {
            Optional<TariffEntity> tariff = tariffRepository.findBySizeAndClientType(consumption.getWaterMeter().getSize().getId()
                    , consumption.getWaterMeter().getClient().getClientType().getId());
            if (tariff.isPresent()) {
                Integer flatFee = tariff.get().getFlatFee();
                var monthConsumption = consumption.getConsumption();
                return monthConsumption * flatFee;
            }
        }
        return 0L;
    }

    public List<ItemEntity> findAll() {
        return itemRepository.findAll();
    }
}
