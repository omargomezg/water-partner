package com.hardnets.coop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
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

    @NotNull(message = "Client Type is required")
    private GenericListDto clientType;

    @Email
    private String email;

    private String telephone;

    private Boolean isActive = true;

    private Set<String> waterMeters = new HashSet<>();

    public ClientDto(String rut, String names, String middleName, String lastName, String businessName, String value, Long clientTypeId, String code) {
        setRut(rut);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setBusinessName(businessName);
        clientType = GenericListDto.builder().id(clientTypeId).value(value).code(code).build();
        generateFullName();
    }

    public ClientDto(String rut, String names, String middleName, String lastName, Date birthDate, Date dateOfAdmission,
                     String businessName, String businessActivity, String telephone, String email, String value,
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
        clientType = GenericListDto.builder().id(clientTypeId).value(value).code(code).build();
        setIsActive(isActive);
        generateFullName();
    }

    private void generateFullName() {
        if (getBusinessName().isEmpty())
            setFullName(String.format("%s %s %s", getNames(), getMiddleName() != null ? getMiddleName(): "", getLastName()));
        else
            setFullName(getBusinessName());
    }
}
