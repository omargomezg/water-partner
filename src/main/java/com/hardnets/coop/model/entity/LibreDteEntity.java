package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class LibreDteEntity extends BaseEntity {

    /**
     * Código para validar estado del documento en estado temporal
     */
    private String hash;

    @OneToOne(mappedBy = "integration")
    private BillEntity bill;
}

