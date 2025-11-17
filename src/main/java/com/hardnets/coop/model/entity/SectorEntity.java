package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SectorEntity extends BaseEntity {

    @NotNull
    @NotBlank
    private String name;
}
