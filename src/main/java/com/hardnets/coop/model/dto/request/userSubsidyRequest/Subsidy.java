package com.hardnets.coop.model.dto.request.userSubsidyRequest;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class Subsidy {
    private Long id;

    @NotNull
    private Date start;
    @NotNull
    private Date end;
    @NotNull
    private Short percentage;
}
