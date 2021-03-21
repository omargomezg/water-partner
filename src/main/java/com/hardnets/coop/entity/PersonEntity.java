package com.hardnets.coop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import java.util.Date;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersonEntity {
    @Id
    private String rut;

    @Column
    private String names;

    @Column
    private String middleName;

    @Column
    private String lastName;

    @Column
    private Date birthDate;

    @Column
    @Email
    private String email;

    @Column
    private Boolean enabled = true;

    private String password;

    protected PersonEntity(String rut, String names, String middleName, String lastName, String email) {
        setRut(rut);
        setNames(names);
        setMiddleName(middleName);
        setLastName(lastName);
        setEmail(email);
    }

    protected PersonEntity() {
    }
}
