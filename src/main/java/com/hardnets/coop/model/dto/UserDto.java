package com.hardnets.coop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardnets.coop.model.entity.UserEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto {
    @NotNull
    private String dni;
    private List<String> roles;
    private String names;
    private String middleName;
    private String email;
    private String lastName;
    private Date lastLogin;
    private Boolean enabled;

    @JsonIgnore
    private String password;

    public UserDto(UserEntity user) {
        this.setDni(user.getDni());
        this.setEmail(user.getEmail());
        this.setNames(user.getNames());
        this.setMiddleName(user.getMiddleName());
        this.setLastName(user.getLastName());
        if(!user.getProfiles().isEmpty()) {
            this.setRoles(user.getProfiles().stream().map(r -> r.label).toList());
        }
        this.setLastLogin(user.getLastLogin());
        setEnabled(user.getEnabled());
    }

}
