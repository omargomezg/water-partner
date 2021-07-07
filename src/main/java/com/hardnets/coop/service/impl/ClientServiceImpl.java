package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.DropDownNotFoundException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final DropDownListRepository dropDownListRepository;

    @Override
    public ClientDto update(ClientDto clientDto) {
        ClientEntity client = clientRepository.findByRut(clientDto.getRut())
                .orElseThrow(() -> new ClientNotFoundException(clientDto.getRut()));
        DropDownListEntity clientType = dropDownListRepository.findById(clientDto.getClientType().getId())
                .orElseThrow(() -> new DropDownNotFoundException("Client type not found"));

        client.setClientType(clientType);
        if (clientType.getCode().equals("PARTNER")) {
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
        return getClientDto(dbClient);
    }

    @Override
    public List<ClientDto> getUsers(FilterDto filter) {
        List<ClientDto> dbClients = clientRepository.findAllClientsByRutOrNameOrNone(filter.getRut(), filter.getName() != null ? filter.getName().toLowerCase() : null);
        dbClients.forEach(client -> {
            Collection<String> ids = waterMeterRepository.finadAllIdsByClient(client.getRut());
            if (!ids.isEmpty()) {
                client.getWaterMeters().addAll(ids);
            }
        });
        return dbClients;
    }

    @Override
    public ClientDto getByRut(String rut) {
        Optional<ClientDto> client = clientRepository.findUserDtoByRut(rut);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new UserNotFoundException("Client not exists");
        }
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        var client = getClientEntity(clientDto);
        DropDownListEntity clientType = dropDownListRepository.findById(clientDto.getClientType().getId())
                .orElseThrow(() -> new DropDownNotFoundException("Client type not found"));
        client.setClientType(clientType);
        ClientEntity dbClient = clientRepository.save(client);
        return getClientDto(dbClient);
    }

    @Override
    public Collection<PendingPaymentDto> getPendingPayments(String rut) {
        return new ArrayList<>();
    }

    private ClientEntity getClientEntity(ClientDto clientDto) {
        var client = new ClientEntity();
        client.setRut(clientDto.getRut());
        client.setNames(clientDto.getNames());
        client.setMiddleName(clientDto.getMiddleName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setBirthDate(clientDto.getBirthDate());
        client.setDateOfAdmission(clientDto.getDateOfAdmission());
        client.setTelephone(clientDto.getTelephone());
        client.setBusinessActivity(clientDto.getBusinessActivity());
        client.setBusinessName(clientDto.getBusinessName());
        client.setProfession(clientDto.getProfession());
        return client;
    }

    // TODO usar mapper
    private ClientDto getClientDto(ClientEntity client) {
        var clientDto = new ClientDto();
        clientDto.setRut(client.getRut());
        clientDto.setNames(client.getNames());
        clientDto.setMiddleName(client.getMiddleName());
        clientDto.setLastName(client.getLastName());
        clientDto.setBirthDate(client.getBirthDate());
        clientDto.setDateOfAdmission(client.getDateOfAdmission());
        clientDto.setBusinessName(client.getBusinessName());
        clientDto.setBusinessActivity(client.getBusinessActivity());
        clientDto.setTelephone(client.getTelephone());
        clientDto.setEmail(client.getEmail());
        clientDto.setProfession(client.getProfession());
        clientDto.setIsActive(client.getEnabled());
        if (Objects.nonNull(client.getClientType())) {
            clientDto.getClientType().setId(client.getClientType().getId());
            clientDto.getClientType().setValue(client.getClientType().getValue());
            clientDto.getClientType().setCode(client.getClientType().getCode());
        }
        clientDto.setFullName(getFullName(clientDto));
        client.getWaterMeter().forEach(item -> clientDto.getWaterMeters().add(item.getNumber()));
        return clientDto;
    }

    private String getFullName(ClientDto clientDto) {
        if (Objects.nonNull(clientDto.getBusinessName())) {
            return clientDto.getBusinessName();
        }
        return new StringBuilder()
                .append(clientDto.getNames())
                .append(" ")
                .append(clientDto.getMiddleName() != null ? clientDto.getMiddleName() + " " : "")
                .append(clientDto.getLastName())
                .toString();
    }
}
