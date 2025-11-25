package com.hardnets.coop.controller;

import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.SaleDocumentService;
import com.hardnets.coop.service.TariffService;
import com.hardnets.coop.service.impl.ConsumptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(controllers = ClosePeriodController.class)
class ClosePeriodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TariffService tariffService;

    @MockitoBean
    private PeriodService periodService;

    @MockitoBean
    private ConsumptionService consumptionService;

    @MockitoBean
    private SaleDocumentService<BillEntity> billService;

    @Test
    void closePeriod_success() throws Exception {
        var content = "{\"id\":1,\"startDate\":\"2021-10-01T03:00:00.000+00:00\",\"endDate\":\"2021-10-31T03:00:00" +
                ".000+00:00\",\"status\":\"ACTIVE\"}";

        when(tariffService.hasTariffForAllDiameters()).thenReturn(true);
        when(periodService.close(1L)).thenReturn(mock(PeriodEntity.class));

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/closing-period/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .with(user("user"))
                                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }
}