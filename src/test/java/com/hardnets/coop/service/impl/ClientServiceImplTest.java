package com.hardnets.coop.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.ClientTypeEntity;
import com.hardnets.coop.model.entity.SectorEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.SectorRepository;
import com.hardnets.coop.repository.WaterMeterRepository;


@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientTypeRepository clientTypeRepository;

    @Mock
    private WaterMeterRepository waterMeterRepository;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ConversionService conversionService;

    @Test
    void update() {
    }

    @Test
    void getUsers_success() {
        /*var clients = List.of(
                new ClientEntity(),
                new ClientEntity()
        );
        FilterDto filter = new FilterDto();
        when(clientRepository.findAllClientsByRutOrNameOrNone(any(), any())).thenReturn(clients);
        Collection<ClientDto> users = clientService.getUsers(filter);
        assertEquals(2, users.size());*/
    }

    @Test
    void getByRut() {
    }

    @Test
    void create_success() {
        ClientDto client = getClient();
        client.setEmail("omar.fdo.gomez@gmail.com");
        when(clientTypeRepository.findById(any())).thenReturn(Optional.of(mock(ClientTypeEntity.class)));
        when(sectorRepository.findById(any())).thenReturn(Optional.ofNullable(mock(SectorEntity.class)));
        when(conversionService.convert(any(), eq(ClientEntity.class))).thenReturn(mock(ClientEntity.class));
        when(conversionService.convert(any(), eq(ClientDto.class))).thenReturn(mock(ClientDto.class));
        when(clientRepository.save(any())).thenReturn(mapToClientEntity(client));

        ClientDto result = clientService.create(client);
        assertNotNull(result);
    }

    private ClientEntity mapToClientEntity(ClientDto client) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setEmail(client.getEmail());
        return clientEntity;
    }

    private ClientDto getClient() {
        return ClientDto.builder()
                .dni("14081226-9")
                .clientType(1L)
                .build();
    }
}
