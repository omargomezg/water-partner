package com.hardnets.coop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardnets.coop.model.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class UserDto {
    private String dni;
    private String role;
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
        if(Objects.nonNull(user.getProfile())) {
            this.setRole(user.getProfile().label);
        }
        this.setLastLogin(user.getLastLogin());
        setEnabled(user.getEnabled());
    }

}
