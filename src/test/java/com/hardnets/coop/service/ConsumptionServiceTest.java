package com.hardnets.coop.service;

import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.ConsumptionRepositoryCrud;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.impl.BillDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumptionServiceTest {

    @Mock
    ConsumptionRepository consumptionRepository;

    @Mock
    ConsumptionRepositoryCrud consumptionRepositoryCrud;

    @Mock
    WaterMeterRepository waterMeterRepository;

    @Mock
    PeriodRepository periodRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    BillDetailService billDetailService;

    @InjectMocks
    ConsumptionService consumptionService;

    @BeforeEach
    void setUp() {
        // MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_success() {
        Integer actualReading = 90;
        WaterMeterEntity entity = new WaterMeterEntity();
        PeriodEntity period = new PeriodEntity();
        var consumption = new ConsumptionEntity();
        consumption.setReading(actualReading);
        when(consumptionRepositoryCrud.save(any(ConsumptionEntity.class))).thenReturn(consumption);
        var result = consumptionService.create(entity, 12, period);
        assertEquals(90, result.getReading());
    }

    @Test
    void update_success() {
        var consumption = new ConsumptionEntity();
        consumption.setReading(11);
        when(consumptionService.update(consumption)).thenReturn(consumption);
        var consumptionResult = consumptionService.update(consumption);
        assertEquals(11, consumptionResult.getReading());
    }

    @Test
    void findOneByPeriodAndWaterMeter_success() {
        Long period = 1L;
        Long waterMeter = 1L;
        Optional<PeriodEntity> periodEntity = Optional.of(mock(PeriodEntity.class));
        Optional<WaterMeterEntity> waterMeterEntity = Optional.of(mock(WaterMeterEntity.class));
        Optional<ConsumptionEntity> consumptionEntity = Optional.of(mock(ConsumptionEntity.class));

        when(periodRepository.findById(period)).thenReturn(periodEntity);
        when(waterMeterRepository.findById(period)).thenReturn(waterMeterEntity);
        when(consumptionRepository.findAllByPeriodAndWaterMeter(periodEntity.get().getId(),
                waterMeterEntity.get().getId())).thenReturn(consumptionEntity);

        var result = consumptionService.findOneByPeriodAndWaterMeter(period, waterMeter);
        assertTrue(result.isPresent());
    }

    @Test
    void findOneByPeriodAndWaterMeter_empty() {
        Long period = 1L;
        Long waterMeter = 1L;
        Optional<PeriodEntity> periodEntity = Optional.of(mock(PeriodEntity.class));
        Optional<WaterMeterEntity> waterMeterEntity = Optional.empty();

        when(periodRepository.findById(period)).thenReturn(periodEntity);
        when(waterMeterRepository.findById(period)).thenReturn(waterMeterEntity);

        var result = consumptionService.findOneByPeriodAndWaterMeter(period, waterMeter);
        assertTrue(result.isEmpty());
    }
}
