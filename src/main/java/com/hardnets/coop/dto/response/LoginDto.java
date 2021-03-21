package com.hardnets.coop.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String rut;
    private String email;
    private String fullName;
    private String token;
}
