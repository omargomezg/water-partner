package com.hardnets.coop.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class SalesDocumentDetailEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Descripción corta del item a cobrar
     */
    @Column
    private String concept;

    /**
     *
     */
    @Column
    private Integer baseAmount;

    /**
     * Consumo
     */
    @Column
    private Integer consumption;

    /**
     * Valor total a cobrar después el calculo en caso de ser necesario
     */
    @Column
    private Integer totalAmount;
}
