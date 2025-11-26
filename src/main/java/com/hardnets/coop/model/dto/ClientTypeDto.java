package com.hardnets.coop.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientTypeDto {
    private long id;

    @NotNull
    @NotBlank
    private String description;
}
