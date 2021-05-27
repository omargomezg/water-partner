package com.hardnets.coop.service;

import com.hardnets.coop.dto.ClientDto;
import com.hardnets.coop.dto.request.FilterDto;
import com.hardnets.coop.dto.response.PendingPaymentDto;

import java.util.Collection;
import java.util.List;

public interface ClientService {

    ClientDto update(ClientDto entity);

    List<ClientDto> getUsers(FilterDto filter);

    ClientDto getByRut(String rut);

    /**
     * Crea un nuevo cliente
     *
     * @param client Dto de cliente
     * @return
     * @throws Exception
     */
    ClientDto create(ClientDto client) throws Exception;

    /**
     * Obtiene los documentos pendientes de pago por parte de un cliente
     *
     * @param rut Rut del deudor
     * @return Un listado de documentos por pagar
     */
    Collection<PendingPaymentDto> getPendingPayments(String rut);
}
