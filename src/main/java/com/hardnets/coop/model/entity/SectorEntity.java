package com.hardnets.coop.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class SectorEntity extends BaseEntity {

    @NotNull
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "sector", cascade= CascadeType.ALL)
    Set<WaterMeterEntity> waterMeters;

}
