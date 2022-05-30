package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.ClientsDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.SectorRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
@Qualifier("clientService")
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final SectorRepository sectorRepository;
    private final WaterMeterRepository waterMeterRepository;
    private final ConversionService conversionService;

    public ClientEntity update(ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        if (clientRepository.findByDni(clientDto.getDni()).isEmpty()) {
            throw new ClientNotFoundException(clientDto.getDni());
        }
        var client = conversionService.convert(clientDto, ClientEntity.class);
        assert client != null;
        var sector = sectorRepository.findById(clientDto.getSector());
        sector.ifPresent(client::setSector);
        ClientEntity dbClient = clientRepository.save(client);
        return conversionService.convert(dbClient, ClientDto.class);
    }

    @Override
    public List<ClientEntity> findAll() {
        return (List<ClientEntity>) clientRepository.findAll();
    }

    @Override
    public ClientsDto getFilteredUsers(FilterDto filter, Integer pageIndex, Integer pageSize) {
        var name = filter.getName() != null ? filter.getName().toLowerCase() : null;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        var clients = clientRepository.findAllClientsByDniOrNameOrNone(filter.getDni(), name, pageable);
        return ClientsDto.builder()
                .totalHits(clients.getTotalElements())
                .items(clients.getContent().stream().map(this::getClientDto).collect(Collectors.toList()))
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
        var meterConverted =  conversionService.convert(meter, WaterMeterDto.class);
        log.info(meterConverted);
        return meterConverted;
    }

    @Override
    public Optional<ClientEntity> getByDni(String dni) {
        return clientRepository.findByDni(dni);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        ClientEntity client = conversionService.convert(clientDto, ClientEntity.class);
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
