package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "client_type")
public class ClientTypeEntity extends BaseEntity {
    private String description;
    private Boolean active;
}
