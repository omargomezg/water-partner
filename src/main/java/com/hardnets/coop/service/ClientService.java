package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.ClientEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientEntity update(ClientEntity entity);

    ClientDto update(ClientDto entity);

    List<ClientDto> getUsers(FilterDto filter);

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

    /**
     * Obtiene los documentos pendientes de pago por parte de un cliente
     *
     * @param rut Rut del deudor
     * @return Un listado de documentos por pagar
     */
    Collection<PendingPaymentDto> getPendingPayments(String rut);

    boolean exist(String rut);
}
