package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.PeriodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class PeriodServiceImplTest {

    @Mock
    private PeriodRepository periodRepository;

    @Mock
    private ModelMapper modelMapper;

    private PeriodServiceImpl periodService;

    @BeforeEach
    void setup() {
        this.periodService = new PeriodServiceImpl(periodRepository, modelMapper);
    }

    @Test
    void update_success() {
        var period = new PeriodDto();
        var periodEntity = modelMapper.map(period, PeriodEntity.class);
        when(periodRepository.save(periodEntity)).thenReturn(periodEntity);
        var result = periodService.update(period);
        assertNotNull(result.getId());
    }

}
