package com.hardnets.coop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardnets.coop.model.entity.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

	@NotNull
	private String dni;
	private List<String> roles;
	private String fullName;
	private String email;
	private Date lastLogin;
	private Boolean enabled;

	@JsonIgnore
	private String password;

    public UserDTO(UserEntity user) {
		this.setDni(user.getDni());
		this.setEmail(user.getEmail());
		this.setFullName(user.getFullName());
		if (!user.getProfiles().isEmpty()) {
			this.setRoles(user.getProfiles().stream().map(r -> r.label).toList());
		}
		this.setLastLogin(user.getLastLogin());
		setEnabled(user.getEnabled());
	}

}
