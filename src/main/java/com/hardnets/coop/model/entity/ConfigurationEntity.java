package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "configuration")
public class ConfigurationEntity extends BaseEntity {
    private String companyName;
}
