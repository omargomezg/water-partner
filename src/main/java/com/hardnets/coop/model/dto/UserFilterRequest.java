package com.hardnets.coop.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserFilterRequest extends PageRequest {
    private String dni;
    private String fullName;
    private String email;

    protected UserFilterRequest() {}
}
