package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.GenericListDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class ClientServiceImplTest {

    private ClientServiceImpl clientService;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private WaterMeterRepository waterMeterRepository;

    @BeforeEach
    void init() {
        clientService = new ClientServiceImpl(clientRepository, waterMeterRepository);
    }

    @Test
    void update() {
    }

    @Test
    void getUsers_success() {
        List<ClientDto> clients = new ArrayList<>();
        clients.add(getClient());
        clients.add(getClient());
        FilterDto filter = new FilterDto();
        when(clientRepository.findAllClientsByRutOrNameOrNone(any(), any())).thenReturn(clients);
        Collection<ClientDto> users = clientService.getUsers(filter);
        assertEquals(2, users.size());
    }

    @Test
    void getByRut() {
    }

    @Test
    void create_success() {
        ClientDto client = getClient();
        client.setEmail("omar.fdo.gomez@gmail.com");
        when(clientRepository.save(any())).thenReturn(mapToClientEntity(client));
        ClientDto result = clientService.create(client);
        assertEquals("omar.fdo.gomez@gmail.com", result.getEmail());
    }

    private ClientEntity mapToClientEntity(ClientDto client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(client.getEmail());
        return clientEntity;
    }

    private ClientDto getClient() {
        return ClientDto.builder()
                .rut("14081226-9")
                .clientType(ClientTypeEnum.PARTNER.label)
                .build();
    }
}
