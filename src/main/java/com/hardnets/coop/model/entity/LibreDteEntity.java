package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class LibreDteEntity extends BaseEntity {

    /**
     * Código para validar estado del documento en estado temporal
     */
    private String hash;

    /**
     * Folio del DTE
     */
    private Long folio;

    @OneToOne(mappedBy = "integration")
    private BillEntity bill;
}

