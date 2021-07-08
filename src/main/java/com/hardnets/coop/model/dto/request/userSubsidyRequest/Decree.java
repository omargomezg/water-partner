package com.hardnets.coop.model.dto.request.userSubsidyRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class Decree {
    @NotNull
    private Integer number;

    @NotNull
    private Date publication;
}
