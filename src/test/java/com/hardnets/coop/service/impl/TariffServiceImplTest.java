package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class TariffServiceImplTest {

    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private WaterMeterRepository waterMeterRepository;

    @Mock
    private ModelMapper modelMapper;

    private TariffServiceImpl tariffService;

    @BeforeEach
    void setup() {
        this.tariffService = new TariffServiceImpl(tariffRepository, waterMeterRepository, modelMapper);
    }

    @Test
    void create_success() {
        var tariffDto = new TariffDto();
        tariffDto.setClientType("7");
        tariffDto.setCubicMeter(20f);
        tariffDto.setFlatFee(1300);
        tariffDto.setId(0L);
        tariffDto.setDiameter("25");
        var tariffEntity = modelMapper.map(tariffDto, TariffEntity.class);
        when(modelMapper.map(tariffDto, TariffEntity.class)).thenReturn(tariffEntity);
        when(tariffRepository.save(tariffEntity)).thenReturn(tariffEntity);
        var result = this.tariffService.create(tariffDto);
    }


}