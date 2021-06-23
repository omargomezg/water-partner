package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.GenericListDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private WaterMeterRepository waterMeterRepository;

    @Mock
    private DropDownListRepository dropDownListRepository;

    @Before
    public void setup() {
        clientService = new ClientServiceImpl(clientRepository, waterMeterRepository, dropDownListRepository);
    }

    @Test
    public void update_success() {
        ClientDto clientDto = new ClientDto();
        clientDto.setRut("123425322");
        clientDto.setClientType(new GenericListDto());
        clientDto.getClientType().setId(1L);

        DropDownListEntity clientType = new DropDownListEntity();
        clientType.setCode("PARTNER");

        ClientEntity client = mock(ClientEntity.class);
        client.setRut("234452345");
        when(clientRepository.findByRut("123425322")).thenReturn(Optional.of(client));
        when(dropDownListRepository.findById(1L)).thenReturn(Optional.of(clientType));
        when(clientRepository.save(client)).thenReturn(client);
        ClientDto response = clientService.update(clientDto);
        assertNotNull(response);
    }

    @Test
    public void getUsers() {
        Collection<ClientDto> users = clientService.getUsers(null);
        assertEquals(users.size(), 2);
    }

    @Test
    public void getByRut() {
    }

    @Test
    public void create() {
        ClientDto client = new ClientDto();
        client.setEmail("omar.fdo.gomez@gmail.com");
        clientService.create(client);
    }
}
