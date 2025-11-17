package com.hardnets.coop.model.dto.request.userSubsidyRequest;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class Decree {
    @NotNull
    private Integer number;

    @NotNull
    private Date publication;
}
