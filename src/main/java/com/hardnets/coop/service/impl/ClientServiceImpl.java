package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
@Qualifier("clientService")
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final WaterMeterRepository waterMeterRepository;

    public ClientEntity update(ClientEntity clientEntity) {
        return clientRepository.saveAndFlush(clientEntity);
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        ClientEntity client = clientRepository.findByRut(clientDto.getRut())
                .orElseThrow(() -> new ClientNotFoundException(clientDto.getRut()));
        ClientTypeEnum clientTypeEnum = ClientTypeEnum.valueOf(clientDto.getClientType());
        client.setClientType(clientTypeEnum);
        if (clientTypeEnum.equals(ClientTypeEnum.PARTNER)) {
            client.setBirthDate(clientDto.getBirthDate());
            client.setNames(clientDto.getNames());
            client.setLastName(clientDto.getLastName());
            client.setMiddleName(clientDto.getMiddleName());
            client.setProfession(clientDto.getProfession());
            client.setBusinessName("");
            client.setBusinessActivity("");
        } else {
            client.setBusinessName(clientDto.getBusinessName());
            client.setBusinessActivity(clientDto.getBusinessActivity());
            client.setBirthDate(null);
            client.setNames("");
            client.setLastName("");
            client.setMiddleName("");
            client.setProfession("");
        }
        client.setDateOfAdmission(clientDto.getDateOfAdmission());
        client.setEnabled(clientDto.getIsActive());
        client.setEmail(clientDto.getEmail());
        client.setTelephone(clientDto.getTelephone());
        ClientEntity dbClient = clientRepository.save(client);
        return mapToClientDTO(dbClient);
    }

    @Override
    public List<ClientDto> getUsers(FilterDto filter) {
        List<ClientDto> dbClients = clientRepository.findAllClientsByRutOrNameOrNone(filter.getRut(), filter.getName() != null ? filter.getName().toLowerCase() : null);
        dbClients.forEach(client -> {
            Collection<Integer> ids = waterMeterRepository.findAllIdsByClient(client.getRut());
            if (!ids.isEmpty()) {
                client.getWaterMeters().addAll(ids);
            }
        });
        return dbClients;
    }

    @Override
    public Optional<ClientEntity> getByRut(String rut) {
        return clientRepository.findByRut(rut);
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        ClientEntity client = new ClientEntity(clientDto);
        client.setClientType(ClientTypeEnum.valueOf(clientDto.getClientType()));
        ClientEntity dbClient = clientRepository.save(client);
        return mapToClientDTO(dbClient);
    }

    @Override
    public ClientEntity create(ClientEntity client) {
        return clientRepository.saveAndFlush(client);
    }

    @Override
    public Collection<PendingPaymentDto> getPendingPayments(String rut) {
        return null;
    }

    @Override
    public boolean exist(String rut) {
        return clientRepository.findByRut(rut).isPresent();
    }

    private ClientDto mapToClientDTO(ClientEntity client) {
        ClientDto clientDto = ClientDto.builder()
                .rut(client.getRut())
                .names(client.getNames())
                .middleName(client.getMiddleName())
                .lastName(client.getLastName())
                .businessName(client.getBusinessName())
                .businessActivity(client.getBusinessActivity())
                .birthDate(client.getBirthDate())
                .profession(client.getProfession())
                .dateOfAdmission(client.getDateOfAdmission())
                .email(client.getEmail())
                .isActive(client.getEnabled())
                .telephone(client.getTelephone())
                .build();
        clientDto.setFullName(getFullName(clientDto));
        clientDto.setClientType(client.getClientType().label);
        if (Objects.nonNull(client.getWaterMeter()) && Objects.nonNull(clientDto.getWaterMeters())) {
            client.getWaterMeter().forEach(item -> clientDto.getWaterMeters().add(item.getSerial()));
        }
        return clientDto;
    }

    private String getFullName(ClientDto client) {
        if (Objects.nonNull(client.getBusinessName()))
            return client.getBusinessName();
        else
            return String.format("%s %s %s", client.getNames(), client.getMiddleName() != null ? client.getMiddleName() : "", client.getLastName());
    }
}
