package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class SectorEntity extends BaseEntity {

    @NotNull
    @NotBlank
    private String name;
}
