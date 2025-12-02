package com.hardnets.coop.service;

import java.util.List;
import java.util.Optional;

import com.hardnets.coop.model.dto.ClientDTO;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.entity.ClientEntity;

public interface ClientService {

	ClientEntity update(ClientEntity entity);

	ClientEntity update(ClientDTO entity);

	List<ClientEntity> findAll();

	List<ClientEntity> getFilteredUsers(FilterDto filter, Integer pageIndex, Integer pageSize);

	Long getTotalOfFilteredUsers(FilterDto filter);

	Optional<ClientEntity> getByDni(String dni);

	/**
	 * Crea un nuevo cliente
	 *
	 * @param client Dto de cliente
	 * @return
	 * @throws Exception
	 */
	ClientEntity create(ClientDTO client);

	ClientEntity create(ClientEntity client);

	boolean exist(String rut);
}
