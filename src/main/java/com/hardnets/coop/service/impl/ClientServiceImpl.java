package com.hardnets.coop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.ClientsDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.SectorRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
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
    private final WaterMeterRepository waterMeterRepository;
    private final ConversionService conversionService;
    private final ClientTypeRepository clientTypeRepository;

    @PersistenceContext
    private EntityManager em;

    public ClientEntity update(ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        var clientType = clientTypeRepository.findById(clientDto.getClientType())
                .orElseThrow(() -> new ClientNotFoundException("Client type not found: " + clientDto.getClientType()));
        if (clientRepository.findByDni(clientDto.getDni()).isEmpty()) {
            throw new ClientNotFoundException(clientDto.getDni());
        }
        var client = conversionService.convert(clientDto, ClientEntity.class);
        assert client != null;
        client.setClientType(clientType);
        var sector = sectorRepository.findById(clientDto.getSector());
        sector.ifPresent(client::setSector);
        ClientEntity dbClient = clientRepository.save(client);
        return conversionService.convert(dbClient, ClientDto.class);
    }

    @Override
    public List<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientsDto getFilteredUsers(FilterDto filter, Integer pageIndex, Integer pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> cq = cb.createQuery(ClientEntity.class);
        Root<ClientEntity> root = cq.from(ClientEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getDni() != null) {
            predicates.add(cb.equal(root.get("dni"), filter.getDni()));
        }
        if (filter.getName() != null) {
            predicates.add(cb.equal(root.get("name"), filter.getName()));
        }
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<ClientEntity> result = em.createQuery(cq);

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<ClientEntity> rootCount = countQuery.from(ClientEntity.class);
        countQuery.select(cb.count(rootCount)).where(cb.and(predicates.toArray(new Predicate[0])));
        Long count = em.createQuery(countQuery).getSingleResult();

        return ClientsDto.builder()
                .totalHits(count)
                .items(result.getResultList().stream().map(this::getClientDto).collect(Collectors.toList()))
                .build();
    }

    private ClientDto getClientDto(ClientEntity client) {
        var clientDto = conversionService.convert(client, ClientDto.class);
        if (clientDto != null) {
            var meters = waterMeterRepository.findAllIdsByClient(clientDto.getDni());
            if (!meters.isEmpty()) {
                clientDto.setWaterMeters(
                        waterMeterRepository.findAllIdsByClient(clientDto.getDni()).stream()
                                .map(this::getMeterDto).collect(Collectors.toList())
                );
            }
        }
        return clientDto;
    }

    private WaterMeterDto getMeterDto(WaterMeterEntity meter) {
        var meterConverted = conversionService.convert(meter, WaterMeterDto.class);
        log.info(meterConverted);
        return meterConverted;
    }

    @Override
    public Optional<ClientEntity> getByDni(String dni) {
        return clientRepository.findByDni(dni);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {

        var clientType = clientTypeRepository.findById(clientDto.getClientType())
                .orElseThrow(() -> new ClientNotFoundException("Client type not found: " + clientDto.getClientType()));
        ClientEntity client = conversionService.convert(clientDto, ClientEntity.class);
        assert client != null;
        client.setClientType(clientType);
        var sector = sectorRepository.findById(clientDto.getSector());
        sector.ifPresent(client::setSector);
        var dto = conversionService.convert(clientRepository.save(client), ClientDto.class);
        sector.ifPresent(item -> dto.setSector(item.getId()));
        return dto;
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
