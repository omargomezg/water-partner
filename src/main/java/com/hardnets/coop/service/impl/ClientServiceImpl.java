package com.hardnets.coop.service.impl;

import com.hardnets.coop.dto.ClientDto;
import com.hardnets.coop.entity.ClientEntity;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Qualifier("clientService")
public class ClientServiceImpl implements PersonService<ClientDto, ClientDto> {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    WaterMeterRepository waterMeterRepository;

    @Autowired
    DropDownListRepository dropDownListRepository;

    @Override
    public ClientDto update(ClientDto clientDto) {
        Optional<ClientEntity> client = clientRepository.findByRut(clientDto.getRut());
        if (client.isPresent()) {
            client.get().setNames(clientDto.getNames());
            client.get().setMiddleName(clientDto.getMiddleName());
            client.get().setLastName(clientDto.getLastName());
            client.get().setBirthDate(clientDto.getBirthDate());
            client.get().setDateOfAdmission(clientDto.getDateOfAdmission());
            client.get().setProfession(clientDto.getProfession());
            client.get().setEmail(clientDto.getEmail());
            client.get().setTelephone(clientDto.getTelephone());
        }
        ClientEntity dbClient = clientRepository.save(client.get());
        return new ClientDto(dbClient);
    }

    @Override
    public Collection<ClientDto> getUsers() {
        Collection<ClientDto> dbClient = clientRepository.findAllClientsDto();
        dbClient.forEach(client -> {
            Collection<String> ids = waterMeterRepository.finadAllIdsByClient(client.getRut());
            if (!ids.isEmpty()) {
                client.getWaterMeters().addAll(ids);
            }
        });
        return dbClient;
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
        ClientEntity client = new ClientEntity(clientDto);
        /*
         * if (clientDto.getClientType() != null) {
         *
         * client.setClientType(); }
         */
        ClientEntity dbClient = clientRepository.save(client);
        // Optional<DropDownListEntity> clientType =
        // dropDownListRepository.findById(clientDto.getClientType());
        return new ClientDto(dbClient);
    }

    @Override
    public ClientDto updatePassword(String rut, String password) {
        return null;
    }

}
