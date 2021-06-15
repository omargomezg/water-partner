package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String rut;
    private RoleEnum role;
    private String names;
    private String middleName;
    private String email;
    private String lastName;
    private Date lastAccess;
    private Boolean isActive;
}
