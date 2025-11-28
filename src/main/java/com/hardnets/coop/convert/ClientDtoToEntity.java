package com.hardnets.coop.convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;

@Component
public class ClientDtoToEntity implements Converter<ClientDto, ClientEntity> {
	@Override
	public ClientEntity convert(@NonNull ClientDto source) {
		ClientEntity clientEntity = new ClientEntity();
		clientEntity.setTypeOfDni(DniTypeEnum.valueOf(source.getDniType()));
		clientEntity.setDni(source.getDni());
		clientEntity.setClientNumber(source.getClientNumber());
		clientEntity.setFullName(source.getFullName());
		clientEntity.setBusinessActivity(source.getBusinessActivity());
		clientEntity.setBirthDate(source.getBirthDate());
		clientEntity.setProfession(source.getProfession());
		clientEntity.setDateOfAdmission(source.getDateOfAdmission());
		clientEntity.setEmail(source.getEmail());
		clientEntity.setEnabled(source.getIsActive());
		clientEntity.setTelephone(source.getTelephone());
		return clientEntity;
	}

}