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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TariffServiceImplTest {

    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private WaterMeterRepository waterMeterRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Test
    void create_success() {
        var tariffDto = TariffDto.builder()
                .clientType("7")
                .cubicMeter(20f)
                .flatFee(1300)
                .diameter("25")
                .build();
        when(conversionService.convert(any(), eq(TariffEntity.class))).thenReturn(mock(TariffEntity.class));
        when(conversionService.convert(any(), eq(TariffDto.class))).thenReturn(mock(TariffDto.class));
        when(tariffRepository.save(any())).thenReturn(mock(TariffEntity.class));
        var result = this.tariffService.create(tariffDto);
        assertNotNull(result);
    }


}