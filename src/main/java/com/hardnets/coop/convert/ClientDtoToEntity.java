package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoToEntity implements Converter<ClientDto, ClientEntity> {
    @Override
    public ClientEntity convert(ClientDto clientDto) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setRut(clientDto.getRut());
        clientEntity.setNames(clientDto.getNames());
        clientEntity.setMiddleName(clientDto.getMiddleName());
        clientEntity.setLastName(clientDto.getLastName());
        clientEntity.setBusinessName(clientDto.getBusinessName());
        clientEntity.setBusinessActivity(clientDto.getBusinessActivity());
        clientEntity.setBirthDate(clientDto.getBirthDate());
        clientEntity.setClientType(ClientTypeEnum.valueOf(clientDto.getClientType()));
        clientEntity.setProfession(clientDto.getProfession());
        clientEntity.setDateOfAdmission(clientDto.getDateOfAdmission());
        clientEntity.setEmail(clientDto.getEmail());
        clientEntity.setEnabled(clientDto.getIsActive());
        clientEntity.setTelephone(clientDto.getTelephone());
        return clientEntity;
    }
}
