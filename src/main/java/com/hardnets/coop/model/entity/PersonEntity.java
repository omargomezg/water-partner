package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.constant.NationalityEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
