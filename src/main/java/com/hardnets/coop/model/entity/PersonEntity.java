package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import java.util.Date;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
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

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private CompanyEntity company;

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
