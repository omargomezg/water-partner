package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.constant.NationalityEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersonEntity {
    @Id
    private String dni;

    @Enumerated(EnumType.STRING)
    private DniTypeEnum typeOfDni;

    @Enumerated(EnumType.STRING)
    private NationalityEnum nationality = NationalityEnum.CHILEAN;

    @NotNull
    private String names;

    private String middleName = "";
    private String lastName = "";
    private Date birthDate;

    @Email
    private String email;

    private Boolean enabled = true;

    private String password;

    protected PersonEntity(String dni, String names, String middleName, String lastName, String email, Date birthDate) {
        setDni(dni);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setEmail(email);
        setBirthDate(birthDate);
    }

    protected PersonEntity() {
    }
}
