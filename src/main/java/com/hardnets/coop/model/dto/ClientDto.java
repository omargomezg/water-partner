package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {
    @NotNull(message = "Dni cannot be null")
    private String dni;

    private String fullName;

    private String names;

    private String middleName;

    private String lastName;

    private String businessName;

    private String businessActivity;

    private Date birthDate;

    private String profession;

    private Date dateOfAdmission;

    private Long sector;

    private Integer clientNumber;

    @NotNull(message = "Client Type is required")
    private String clientType;

    @Email
    private String email;

    private String telephone;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private List<WaterMeterDto> waterMeters = new ArrayList<>();

     public ClientDto(String dni, String names, String middleName, String lastName, String businessName,
                      ClientTypeEnum clientType) {
        setDni(dni);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setBusinessName(businessName);
        setClientType(clientType.label);
        generateFullName();
    }


    private void generateFullName() {
        if (getBusinessName().isEmpty())
            setFullName(String.format("%s %s %s", getNames(), getMiddleName() != null ? getMiddleName(): "", getLastName()));
        else
            setFullName(getBusinessName());
    }
}
