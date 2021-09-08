package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
