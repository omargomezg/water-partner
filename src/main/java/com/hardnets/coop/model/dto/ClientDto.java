package com.hardnets.coop.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hardnets.coop.model.entity.ClientTypeEntity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto {

	@NotNull
	private String dniType;

	@NotNull(message = "Dni cannot be null")
	private String dni;

	private String fullName;

	private String businessActivity;

	private Date birthDate;

	private String profession;

	private Date dateOfAdmission;

	private Long sector;

	private Integer clientNumber;

	@NotNull(message = "Client Type is required")
	private Long clientType;

	@Email
	private String email;

	private String telephone;

	@Builder.Default
	private Boolean isActive = true;

	@Builder.Default
	private List<WaterMeterDto> waterMeters = new ArrayList<>();

	public ClientDto(String dni, String fullName, ClientTypeEntity clientType) {
		setDni(dni);
		setFullName(fullName);
		setClientType(clientType != null ? clientType.getId() : null);
	}
}
