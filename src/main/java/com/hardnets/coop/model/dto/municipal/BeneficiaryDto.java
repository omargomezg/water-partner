package com.hardnets.coop.model.dto.municipal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BeneficiaryDto {
	/**
	 * Código: DNI
	 */
	private String dni;

	/**
	 * Código: DV-RUT
	 */
	private String fullName;

	/**
	 * Código: DIRECCION
	 */
	private String address;
}
