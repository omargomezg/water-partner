package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "configuration")
public class ConfigurationEntity extends BaseEntity {
    private String companyName;
}
