package com.hardnets.coop.dto;

import com.hardnets.coop.entity.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
    private String rut;
    private Long roleId;
    private String names;
    private String middleName;
    private String lastName;
    private Date lastAccess;

    public UserDto(UserEntity user) {
        this.setRut(user.getRut());
        this.setNames(user.getNames());
        this.setMiddleName(user.getMiddleName());
        this.setLastName(user.getLastName());
        this.setRoleId(user.getRole().getId());
        this.setLastAccess(user.getLastLogin());
    }

}
