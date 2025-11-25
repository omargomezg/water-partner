package com.hardnets.coop.convert;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class ClientEntityToDto implements Converter<ClientEntity, ClientDto> {
    @Override
    public ClientDto convert(ClientEntity source) {
        var clientDto = ClientDto.builder()
                .dni(source.getDni())
                .fullName(source.getFullName())
                .names(source.getNames())
                .email(source.getEmail())
                .birthDate(source.getBirthDate())
                .telephone(source.getTelephone())
                .clientNumber(source.getClientNumber())
                .profession(getProfession(source))
                .waterMeters(new ArrayList<>());

        if (Objects.nonNull(source.getTypeOfDni())) {
            clientDto.dniType(source.getTypeOfDni().name());
        }

        if (Objects.nonNull(source.getSector())) {
            clientDto.sector(source.getSector().getId());
        }

        return clientDto.build();
    }

    private String getProfession(ClientEntity client) {
        return client.getBusinessName().isEmpty() ? client.getProfession() : "";
    }
}
