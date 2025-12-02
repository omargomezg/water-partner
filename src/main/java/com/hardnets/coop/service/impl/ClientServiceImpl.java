package com.hardnets.coop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.model.dto.ClientDTO;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.SectorRepository;
import com.hardnets.coop.service.ClientService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@Service
@Qualifier("clientService")
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final SectorRepository sectorRepository;
	private final ConversionService conversionService;
	private final ClientTypeRepository clientTypeRepository;

	@PersistenceContext
	private EntityManager em;

	public ClientEntity update(ClientEntity clientEntity) {
		return clientRepository.save(clientEntity);
	}

	@Override
	public ClientEntity update(ClientDTO clientDto) {
		var clientType = clientTypeRepository.findById(clientDto.getClientType().getId())
				.orElseThrow(() -> new ClientNotFoundException("Client type not found: " + clientDto.getClientType()));
		if (clientRepository.findByDni(clientDto.getDni()).isEmpty()) {
			throw new ClientNotFoundException(clientDto.getDni());
		}
		var client = conversionService.convert(clientDto, ClientEntity.class);
		assert client != null;
		client.setClientType(clientType);
		var sector = sectorRepository.findById(clientDto.getSector().getId());
		sector.ifPresent(client::setSector);
		return clientRepository.save(client);
	}

	@Override
	public List<ClientEntity> findAll() {
		return clientRepository.findAll();
	}

	@Override
	public List<ClientEntity> getFilteredUsers(FilterDto filter, Integer pageIndex, Integer pageSize) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ClientEntity> cq = cb.createQuery(ClientEntity.class);
		Root<ClientEntity> root = cq.from(ClientEntity.class);
		List<Predicate> predicates = buildPredicates(filter, cb, root);
		if (!predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[0]));
		}
		TypedQuery<ClientEntity> result = em.createQuery(cq);
		return (List<ClientEntity>) result.getResultList();
	}

	@Override
	public Long getTotalOfFilteredUsers(FilterDto filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ClientEntity> root = cq.from(ClientEntity.class);
		List<Predicate> predicates = buildPredicates(filter, cb, root);
		if (!predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[0]));
		}
		cq.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));
		return em.createQuery(cq).getSingleResult();
	}

	private List<Predicate> buildPredicates(FilterDto filter, CriteriaBuilder cb, Root<ClientEntity> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filter.getDni() != null && !filter.getDni().trim().isEmpty()) {
			predicates.add(cb.equal(root.get("dni"), filter.getDni()));
		}

		if (filter.getName() != null && !filter.getName().trim().isEmpty()) {
			String searchPattern = "%" + filter.getName().toLowerCase() + "%";
			predicates.add(cb.like(cb.lower(root.get("fullName")), searchPattern));
		}

		return predicates;
	}

	@Override
	public Optional<ClientEntity> getByDni(String dni) {
		return clientRepository.findByDni(dni);
	}

	@Override
	public ClientEntity create(ClientDTO clientDto) {
		var clientType = clientTypeRepository.findById(clientDto.getClientType().getId())
				.orElseThrow(() -> new ClientNotFoundException("Client type not found: " + clientDto.getClientType()));
		ClientEntity client = conversionService.convert(clientDto, ClientEntity.class);
		client.setClientType(clientType);
		if (clientDto.getSector() != null) {
			var sector = sectorRepository.findById(clientDto.getSector().getId());
			sector.ifPresent(client::setSector);
		}
		return clientRepository.save(client);
	}

	@Override
	public ClientEntity create(ClientEntity client) {
		return clientRepository.save(client);
	}

	@Override
	public boolean exist(String rut) {
		return clientRepository.findByDni(rut).isPresent();
	}

}
