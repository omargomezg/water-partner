package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.TariffRepository;
import lombok.AllArgsConstructor;import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ItemCalculationService {

    private final TariffRepository tariffRepository;
    private final SubsidyRepository subsidyRepository;

    /**
     * @return el monto subsidiado
     */
    public Integer getSubsidyAmount(ConsumptionEntity consumption) {
        Optional<SubsidyEntity> subsidy =
                subsidyRepository.findAllByWaterMeterAndIsActiveAndEndingDateAfter(consumption.getWaterMeter().getId(), true,
                        new Date());
        if (subsidy.isPresent()) {
            short percentage = subsidy.get().getPercentage();
            Integer total = getConsumptionAmount(consumption);
            return (total * percentage) / 100;
        }
        return 0;
    }

    /**
     * @return el monto a cobrar por exceso de consumo
     */
    public Integer getExcessAmount() {
        return 0;
    }

    /**
     * @return El monto a cobrar por consumo
     */
    public Integer getConsumptionAmount(ConsumptionEntity consumption) {
        if (consumption.getWaterMeter().getClient() != null) {
            Optional<TariffEntity> tariff = tariffRepository.findBySizeAndClientType(consumption.getWaterMeter().getDiameter()
                    , consumption.getWaterMeter().getClient().getClientType());
            if (tariff.isPresent()) {
                Integer flatFee = tariff.get().getFlatFee();
                var monthConsumption = consumption.getReading();
                return monthConsumption * flatFee;
            }
        }
        return 0;
    }
}
