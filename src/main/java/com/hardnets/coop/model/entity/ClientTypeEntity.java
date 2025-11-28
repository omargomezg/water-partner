package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_type")
public class ClientTypeEntity extends BaseEntity {

	/**
	 * Descripción del tipo de cliente
	 */
	private String description;

	/**
	 * Código único para identificar el tipo de cliente
	 */
	private String code;

	/**
	 * Indica si el tipo de cliente está activo o inactivo
	 */
	private Boolean active;
}
