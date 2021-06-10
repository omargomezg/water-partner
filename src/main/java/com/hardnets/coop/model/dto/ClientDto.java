package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.entity.ClientEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {
    @NotNull(message = "Rut cannot be null")
    private String rut;

    private String fullName;

    private String names;

    private String middleName;

    private String lastName;

    private String businessName;

    private String businessActivity;

    private Date birthDate;

    private String profession;

    private Date dateOfAdmission;

    private GenericListDto clientType = new GenericListDto();

    @Email
    private String email;

    private String telephone;

    private Boolean isActive = true;

    private Set<String> waterMeters = new HashSet<>();

    public ClientDto(String rut, String names, String middleName, String lastName, String businessName, String clientType, Long clientTypeId, String code) {
        setRut(rut);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setBusinessName(businessName);
        getClientType().setId(clientTypeId);
        getClientType().setValue(clientType);
        getClientType().setCode(code);
        generateFullName();
    }

    public ClientDto(String rut, String names, String middleName, String lastName, Date birthDate, Date dateOfAdmission,
                     String businessName, String businessActivity, String telephone, String email, String clientType,
                     Long clientTypeId, String code, boolean isActive) {
        setRut(rut);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setBirthDate(birthDate);
        setDateOfAdmission(dateOfAdmission);
        setBusinessName(businessName);
        setBusinessActivity(businessActivity);
        setTelephone(telephone);
        setEmail(email);
        getClientType().setId(clientTypeId);
        getClientType().setValue(clientType);
        getClientType().setCode(code);
        setIsActive(isActive);
        generateFullName();
    }

    public ClientDto(ClientEntity client) {
        this.setRut(client.getRut());
        this.setNames(client.getNames());
        this.setMiddleName(client.getMiddleName());
        this.setLastName(client.getLastName());
        this.setBusinessName(client.getBusinessName());
        this.setBusinessActivity(client.getBusinessActivity());
        this.setBirthDate(client.getBirthDate());
        this.setProfession(client.getProfession());
        this.setDateOfAdmission(client.getDateOfAdmission());
        this.getClientType().setId(client.getClientType().getId());
        if (client.getClientType() != null) {
            this.getClientType().setValue(client.getClientType().getValue());
        }
        this.setEmail(client.getEmail());
        this.setIsActive(client.getEnabled());
        this.setTelephone(client.getTelephone());
        try {
            if (client.getWaterMeter() != null) {
                client.getWaterMeter().forEach(item -> this.getWaterMeters().add(item.getNumber()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        generateFullName();
    }

    private void generateFullName() {
        if (getBusinessName().isEmpty())
            setFullName(String.format("%s %s %s", getNames(), getMiddleName() != null ? getMiddleName(): "", getLastName()));
        else
            setFullName(getBusinessName());
    }
}
