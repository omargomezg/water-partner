package com.hardnets.coop.model.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserSignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
