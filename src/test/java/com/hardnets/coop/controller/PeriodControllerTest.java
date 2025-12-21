package com.hardnets.coop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hardnets.coop.service.PeriodService;

@ActiveProfiles("test")
@WebMvcTest(controllers = PeriodController.class)
class PeriodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PeriodService periodService;

    @Test
    void tets_success() throws Exception {
        when(periodService.findAll(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/period")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("user"))
                                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void tets_success_with_status() throws Exception {
        when(periodService.findAll(any())).thenReturn(new ArrayList<>());
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/period")
                                .param("status", "ACTIVE")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(user("user"))
                                .with(csrf()))
                .andExpect(status().isOk())
                .andReturn();
    }


}