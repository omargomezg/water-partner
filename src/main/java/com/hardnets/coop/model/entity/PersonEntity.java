package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    private String rut;

    @Column
    @NotNull
    private String names;

    @Column
    private String middleName = "";

    @Column
    private String lastName = "";

    @Column
    private Date birthDate;

    @Column
    @Email
    private String email;

    @Column
    private Boolean enabled = true;

    private String password;

    protected PersonEntity(String rut, String names, String middleName, String lastName, String email, Date birthDate) {
        setRut(rut);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setEmail(email);
        setBirthDate(birthDate);
    }

    protected PersonEntity() {
    }
}
