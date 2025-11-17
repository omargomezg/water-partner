package com.hardnets.coop.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserSignupRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
