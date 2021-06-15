package com.hardnets.coop.model.dto.response;

import com.hardnets.coop.model.constant.RoleEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Getter
@Setter
public class LoginDto {
    private String rut;
    private String email;
    private String fullName;
    private String token;
    private RoleEnum role;
    private String companyName;
    private String shortCompanyName;
}
