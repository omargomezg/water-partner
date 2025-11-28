package com.hardnets.coop.convert;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;

@Component
public class ClientEntityToDto implements Converter<ClientEntity, ClientDto> {
	@Override
	public ClientDto convert(ClientEntity source) {
		var clientDto = ClientDto.builder().dni(source.getDni()).fullName(source.getFullName()).email(source.getEmail())
				.birthDate(source.getBirthDate()).telephone(source.getTelephone())
				.clientNumber(source.getClientNumber()).profession(source.getProfession())
				.waterMeters(new ArrayList<>());

		if (Objects.nonNull(source.getTypeOfDni())) {
			clientDto.dniType(source.getTypeOfDni().name());
		}

		if (Objects.nonNull(source.getSector())) {
			clientDto.sector(source.getSector().getId());
		}

		return clientDto.build();
	}
}
