package com.hardnets.coop.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateUserDto {
    @NotNull(message = "Rut cannot be null")
    private String rut;
    @NotNull(message = "need a rol")
    private Long roleId;
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Name cannot be null")
    private String names;
    private String middleName;
    private String lastName;
    private String email;
}
