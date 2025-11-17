package com.hardnets.coop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hardnets.coop.model.entity.UserEntity;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Objects;

public class UserDto {
    @NotNull
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

    public UserDto() {
    }

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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

}
