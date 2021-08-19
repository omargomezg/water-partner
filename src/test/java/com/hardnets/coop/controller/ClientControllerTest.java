package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.GenericListDto;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.utils.JsonUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @Before
    public void setup() {

    }

    @Test
    void getUsers() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/client")
                        .with(user("user")))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(0, result.getResponse().getContentLength());
    }

    @Test
    void testGetUsers() {
    }

    @Test
    void createUser_success() throws Exception {
        ClientDto client = getClient();
        when(clientService.create(client)).thenReturn(client);
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/client")
                        .with(user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(client)))
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void updateUser_success() throws Exception {
        ClientDto client = getClient();
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/client")
                        .with(user("user"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(client)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void addWaterMeter() {
    }

    @Test
    void getWaterMeters() {
    }

    private ClientDto getClient() {
        GenericListDto clientType = GenericListDto.builder()
                .id(12L)
                .value("Socio")
                .code("PARTNER")
                .build();
        return ClientDto.builder()
                .rut("14081226-9")
                .clientType(clientType)
                .build();
    }
}
