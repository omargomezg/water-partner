package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.PeriodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeriodServiceImplTest {

    @Mock
    private PeriodRepository periodRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private PeriodServiceImpl periodService;

    @BeforeEach
    void setup() {
    }

    @Test
    void update_success() {
        var period = new PeriodDto();

        when(conversionService.convert(any(), eq(PeriodEntity.class))).thenReturn(new PeriodEntity());
        when(periodRepository.findById(any())).thenReturn(Optional.of(mock(PeriodEntity.class)));
        when(periodRepository.save(any())).thenReturn(mock(PeriodEntity.class));
        when(modelMapper.map(any(), eq(PeriodDto.class))).thenReturn(mock(PeriodDto.class));

        var result = periodService.update(period);
        assertNotNull(result.getId());
    }

}
