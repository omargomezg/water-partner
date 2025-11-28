package com.hardnets.coop.model.entity;

import java.util.Date;

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

	/**
	 * Puede ser un nombre completo o un nombre de fantasía
	 */
	@NotNull
	private String fullName;

	private Date birthDate;

	@Email
	private String email;

	private Boolean enabled = true;

	private String password;

	protected PersonEntity(String dni, String fullName, String email, Date birthDate) {
		setFullName(fullName);
		setDni(dni);
		setEmail(email);
		setBirthDate(birthDate);
	}

	protected PersonEntity() {
	}
}
