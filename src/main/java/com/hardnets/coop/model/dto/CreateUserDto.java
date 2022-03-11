package com.hardnets.coop.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserDto {
    @NotNull(message = "Dni cannot be null")
    private String dni;
    @NotNull(message = "need a rol")
    private String role;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Name cannot be null")
    private String names;
    private String middleName;
    private String lastName;
    private String email;
}
