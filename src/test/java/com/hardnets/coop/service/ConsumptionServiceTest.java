package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDto;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.impl.BillDetailService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsumptionServiceTest {
    private ConsumptionService consumptionService;

    @Mock
    private ConsumptionRepository consumptionRepository;
    @Mock
    private WaterMeterRepository waterMeterRepository;
    @Mock
    private PeriodRepository periodRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private BillDetailService billDetailService;

    @Before
    public void setUp() {
        consumptionService = new ConsumptionService(consumptionRepository, waterMeterRepository, periodRepository,
                clientRepository, billDetailService);
    }

    @Test
    public void findAllByPeriodId_success() {
        List<ResumeConsumptionDetailDto> detail = new ArrayList<>();
        detail.add(mock(ResumeConsumptionDetailDto.class));
        detail.add(mock(ResumeConsumptionDetailDto.class));
        detail.add(mock(ResumeConsumptionDetailDto.class));
        Page<ResumeConsumptionDetailDto> page = new PageImpl(detail);
        Pageable pageable = PageRequest.of(0, 25);
        PeriodEntity period = new PeriodEntity();

        period.setId(12L);
        when(periodRepository.findById(12L)).thenReturn(Optional.of(period));
        when(periodRepository.findFirstByIdNot(period.getId())).thenReturn(mock(PeriodEntity.class));
        when(consumptionRepository.findAllByPeriod(period.getId(), pageable)).thenReturn(page);
        when(consumptionRepository.findById(0L)).thenReturn(Optional.of(mock(ConsumptionEntity.class)));
        ResumeConsumptionDto resume = consumptionService.findAllByPeriodId(period.getId(), 0, 25);
        assertNotNull(resume);
    }
}
