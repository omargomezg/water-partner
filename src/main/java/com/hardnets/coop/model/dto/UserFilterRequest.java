package com.hardnets.coop.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserFilterRequest extends PageRequest {
    private String dni;
    private String fullName;
    private String email;
}
