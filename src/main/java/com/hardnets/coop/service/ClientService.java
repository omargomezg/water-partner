package com.hardnets.coop.service;

import java.util.List;
import java.util.Optional;

import com.hardnets.coop.model.dto.ClientFilterRequest;
import com.hardnets.coop.model.dto.ClientRequestDTO;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;

public interface ClientService {

	ClientEntity update(ClientEntity entity);

	ClientEntity update(ClientRequestDTO clientRequestDTO);

	List<ClientEntity> findAll();

	List<ClientEntity> getFilteredUsers(ClientFilterRequest filter);

	Long getTotalOfFilteredUsers(ClientFilterRequest filter);

	Optional<ClientEntity> getByDni(String dni);

	/**
	 * Crea un nuevo cliente
	 *
	 * @param client Dto de cliente
	 * @return
	 * @throws Exception
	 */
	ClientEntity create(ClientRequestDTO client);

	ClientEntity create(ClientEntity client);

	boolean exist(String rut);

	void deleteByDni(String dni);

	ClientEntity addWaterMeter(ClientEntity client, WaterMeterEntity waterMeter);
}
