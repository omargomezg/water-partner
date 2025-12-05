package com.hardnets.coop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.dto.ClientDTO;
import com.hardnets.coop.model.dto.ClientRequestDTO;
import com.hardnets.coop.model.dto.ClientTypeDTO;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.service.impl.BillServiceImpl;
import com.hardnets.coop.utils.JsonUtil;

@ActiveProfiles("test")
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ClientService clientService;

	@MockitoBean
	private BillServiceImpl billServiceImpl;

	@Autowired
	private ConversionService conversionService;

	@Test
	void getUsers_success() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v1/client").param("pageIndex", "0")
				.param("pageSize", "30").with(user("user")).with(csrf())).andExpect(status().isOk()).andReturn();
		assertEquals(0, result.getResponse().getContentLength());
	}

	@Test
	void testGetUsers() {
	}

	@Test
	void createUser_success() throws Exception {
		ClientDTO client = getClient();
		when(clientService.create(any(ClientRequestDTO.class))).thenReturn(mock(ClientEntity.class));
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/client").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(client)).with(csrf()).with(user("user"))).andExpect(status().isCreated())
				.andDo(MockMvcResultHandlers.print()).andReturn();
	}

	@Test
	void updateUser_success() throws Exception {
		ClientDTO client = getClient();
		mockMvc.perform(MockMvcRequestBuilders.put("/v1/client").with(user("user")).with(csrf())
				.contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(client))).andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void addWaterMeter() {
	}

	@Test
	void getWaterMeters() {
	}

	private ClientDTO getClient() {
		return ClientDTO.builder().dni("14081226-9").typeOfDni(DniTypeEnum.CHILEAN)
				.clientType(ClientTypeDTO.builder().id(1L).build()).build();
	}
}
