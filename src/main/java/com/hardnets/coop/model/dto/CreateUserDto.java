package com.hardnets.coop.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
	@NotNull(message = "Dni cannot be null")
	private String dni;
	@NotNull(message = "need a rol")
	private String role;
	@NotNull(message = "Password is required")
	private String password;

	@NotNull(message = "Name cannot be null")
	private String fullName;
	private String email;
}
