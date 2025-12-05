package com.hardnets.coop.controller;

import com.hardnets.coop.service.impl.WaterMeterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WaterMeterController.class)
class WaterMeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WaterMeterService waterMeterService;

    @Test
    void getWaterMeters() throws Exception {
        mockMvc.perform(get("/v1/water-meter")
                        .with(user("user").password("password")))
                .andExpect(status().isOk());

    }

    @Test
    void addWaterMeter_Unauthenticated_ShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/v1/water-meter"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void addWaterMeter_Authenticated_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/v1/water-meter")
                        .with(user("user").password("password")))
                .andExpect(status().isOk());
    }

}