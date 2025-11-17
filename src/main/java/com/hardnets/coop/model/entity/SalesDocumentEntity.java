package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@MappedSuperclass
public abstract class SalesDocumentEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Indica el estado actual de un documento (boleta)
     */
    @Column
    private SalesDocumentStatusEnum status;

    /**
     * Numero de la boleta/factura
     */
    @Column
    private String documentNumber;

    /**
     * Fecha de emisión
     */
    @Column
    private Date dateOfEmission;
}
