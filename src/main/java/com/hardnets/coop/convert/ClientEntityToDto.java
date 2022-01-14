package com.hardnets.coop.convert;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientEntityToDto implements Converter<ClientEntity, ClientDto> {
    @Override
    public ClientDto convert(ClientEntity clientEntity) {
        return ClientDto.builder()
                .rut(clientEntity.getRut())
                .fullName(getFullName(clientEntity))
                .names(clientEntity.getNames())
                .clientType(clientEntity.getClientType().label)
                .birthDate(clientEntity.getBirthDate())
                .waterMeters(new ArrayList<>())
                .build();
    }

    private String getFullName(ClientEntity client) {
        if (client.getBusinessName().isEmpty())
            return String.format("%s %s %s",
                    client.getNames(),
                    client.getMiddleName() != null ? client.getMiddleName(): "", client.getLastName());
        else
            return client.getBusinessName();
    }
}
