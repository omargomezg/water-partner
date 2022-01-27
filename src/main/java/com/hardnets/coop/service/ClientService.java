package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.ClientsDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.ClientEntity;

import java.util.Collection;
import java.util.Optional;

public interface ClientService {

    ClientEntity update(ClientEntity entity);

    ClientDto update(ClientDto entity);

    ClientsDto getUsers(FilterDto filter, Integer pageIndex, Integer pageSize);

    Optional<ClientEntity> getByRut(String rut);

    /**
     * Crea un nuevo cliente
     *
     * @param client Dto de cliente
     * @return
     * @throws Exception
     */
    ClientDto create(ClientDto client);

    ClientEntity create(ClientEntity client);

    boolean exist(String rut);
}
