package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.ClientTypeEnum;
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
    private String clientType;

    @Email
    private String email;

    private String telephone;

    private Boolean isActive = true;

    private Set<Integer> waterMeters = new HashSet<>();

    public ClientDto(String rut, String names, ClientTypeEnum clientType) {
        setRut(rut);
        setNames(names);
        setClientType(clientType.label);
        generateFullName();
    }

    public ClientDto(String rut, String names, String middleName, String lastName, String businessName,
                     ClientTypeEnum clientType) {
        setRut(rut);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setBusinessName(businessName);
        setClientType(clientType.label);
        generateFullName();
    }

    public ClientDto(String rut, String names, String middleName, String lastName, Date birthDate, Date dateOfAdmission,
                     String businessName, String businessActivity, String telephone, String email, ClientTypeEnum clientType,
                     boolean isActive) {
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
        setClientType(clientType.label);
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
