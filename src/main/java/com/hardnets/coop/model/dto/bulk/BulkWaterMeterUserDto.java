package com.hardnets.coop.model.dto.bulk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BulkWaterMeterUserDto {

    @NotEmpty
    private String rut;

    @NotEmpty
    private String names;

    @NotEmpty
    @NotNull
    private Integer diameter;

    @NotNull
    @NotEmpty
    private Integer serial = 0;

    @NotNull
    @NotEmpty
    private Integer reading;

}
