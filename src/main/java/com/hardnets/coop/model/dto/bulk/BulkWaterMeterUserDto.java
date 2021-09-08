package com.hardnets.coop.model.dto.bulk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BulkWaterMeterUserDto {

    @NotEmpty
    private String rut;

    @NotEmpty
    private String names;

    @NotEmpty
    @NotNull
    private Short diameter;

    @NotNull
    @NotEmpty
    private Integer series;

    @NotNull
    @NotEmpty
    private Integer reading;

}
