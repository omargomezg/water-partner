package com.hardnets.coop.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ClosePeriodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void closePeriod() throws Exception {
        var content = "{\"id\":1,\"startDate\":\"2021-10-01T03:00:00.000+00:00\",\"endDate\":\"2021-10-31T03:00:00" +
                ".000+00:00\",\"status\":\"ACTIVE\"}";
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/closing-period/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .with(user("user")))
                .andExpect(status().isOk())
                .andReturn();
    }
}