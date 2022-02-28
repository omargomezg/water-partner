package com.hardnets.coop.convert;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientEntityToDto implements Converter<ClientEntity, ClientDto> {
    @Override
    public ClientDto convert(ClientEntity source) {
        var clientDto = ClientDto.builder()
                .rut(source.getRut())
                .fullName(getFullName(source))
                .names(source.getNames())
                .email(source.getEmail())
                .clientType(source.getClientType().label)
                .birthDate(source.getBirthDate())
                .telephone(source.getTelephone())
                .clientNumber(source.getClientNumber())
                .profession(getProfession(source));

        if (Objects.nonNull(source.getSector())) {
            clientDto.sector(source.getSector().getId());
        }

        return clientDto.build();
    }

    private String getProfession(ClientEntity client) {
        return client.getBusinessName().isEmpty() ? client.getProfession() : "";
    }

    private String getFullName(ClientEntity client) {
        if (client.getBusinessName().isEmpty())
            return String.format("%s %s %s",
                    client.getNames(),
                    client.getMiddleName() != null ? client.getMiddleName() : "", client.getLastName());
        else
            return client.getBusinessName();
    }
}
