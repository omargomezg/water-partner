package com.hardnets.coop.service;

public interface ClientMeterService {

    /**
     * Elimina una relación entre medidor y un cliente
     * @param meter Identificador del medidor
     * @param dni Cliente relacionado
     */
    void delete (Long meter, String dni);
}
