package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ClientDtoToEntity implements Converter<ClientDto, ClientEntity> {
    @Override
    public ClientEntity convert(@NonNull ClientDto source) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setTypeOfDni(DniTypeEnum.valueOf(source.getDniType()));
        clientEntity.setDni(source.getDni());
        clientEntity.setClientNumber(source.getClientNumber());
        clientEntity.setNames(source.getNames());
        clientEntity.setMiddleName(source.getMiddleName());
        clientEntity.setLastName(source.getLastName());
        clientEntity.setBusinessName(source.getBusinessName());
        clientEntity.setFullName(getFullName(source));
        clientEntity.setBusinessActivity(source.getBusinessActivity());
        clientEntity.setBirthDate(source.getBirthDate());
        clientEntity.setClientType(ClientTypeEnum.valueOf(source.getClientType()));
        clientEntity.setProfession(source.getProfession());
        clientEntity.setDateOfAdmission(source.getDateOfAdmission());
        clientEntity.setEmail(source.getEmail());
        clientEntity.setEnabled(source.getIsActive());
        clientEntity.setTelephone(source.getTelephone());
        return clientEntity;
    }

    private String getFullName(ClientDto source) {
        if (source.getBusinessName().isEmpty()) {
            StringBuilder sb = new StringBuilder().append(source.getNames());
            if (Objects.nonNull(source.getMiddleName())) {
                sb.append(" ").append(source.getMiddleName());
            }
            if (Objects.nonNull(source.getLastName())) {
                sb.append(" ").append(source.getLastName());
            }
            return sb.toString();
        } else
            return source.getBusinessName();
    }

}
