package com.hardnets.coop.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.ClientTypeDTO;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.entity.ClientTypeEntity;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;

@ExtendWith(MockitoExtension.class)
class TariffServiceImplTest {

    
    @Mock
    private TariffRepository tariffRepository;

    @Mock
    private WaterMeterRepository waterMeterRepository;

    @Mock
    private ClientTypeRepository clientTypeRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Test
    void create_success() {
    	var clientType = new ClientTypeDTO();
    	clientType.setId(1L);
        var tariffDto = TariffDto.builder()
                .clientType(clientType)
                .cubicMeter(20f)
                .flatFee(1300)
                .diameter(DiameterEnum.NINETEEN)
                .build();
        when(clientTypeRepository.findById(any())).thenReturn(java.util.Optional.of(mock(ClientTypeEntity.class)));
        when(conversionService.convert(any(), eq(TariffEntity.class))).thenReturn(mock(TariffEntity.class));
        when(conversionService.convert(any(), eq(TariffDto.class))).thenReturn(mock(TariffDto.class));
        when(tariffRepository.save(any())).thenReturn(mock(TariffEntity.class));
        var result = this.tariffService.create(tariffDto);
        assertNotNull(result);
    }


}