package com.hardnets.coop.model.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.constant.NationalityEnum;
import com.hardnets.coop.model.dto.views.AppViews;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class PersonEntity {
	@Id
	@JsonView(AppViews.Public.class)
	private String dni;

	@Enumerated(EnumType.STRING)
	private DniTypeEnum typeOfDni;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@JsonView(AppViews.Public.class)
	private NationalityEnum nationality = NationalityEnum.CHILEAN;

	/**
	 * Puede ser un nombre completo o un nombre de fantasía
	 */
	@NotNull
	@JsonView(AppViews.Public.class)
	private String fullName;

	private Date birthDate;

	@Email
	private String email;

	@Builder.Default
	private Boolean enabled = true;

	private String password;

}
