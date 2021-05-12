package com.hardnets.coop.service.impl;

import com.hardnets.coop.dto.ClientDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ClientServiceImplTest {

    @Autowired
    ClientServiceImpl clientService;

    @Test
    void update() {
    }

    @Test
    void getUsers() {
        Collection<ClientDto> users = clientService.getUsers(null);
        assertEquals(users.size(), 2);
    }

    @Test
    void getByRut() {
    }

    @Test
    void create() {
        ClientDto client = new ClientDto();
        client.setEmail("omar.fdo.gomez@gmail.com");
        clientService.create(client);
    }
}
