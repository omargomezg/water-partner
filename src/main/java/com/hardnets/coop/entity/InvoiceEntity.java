package com.hardnets.coop.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "invoice")
public class InvoiceEntity extends BaseEntity {

    /**
     * Columna puede contener los siguientes valores:
     * - DRAFT: Borrador
     * - PENDING: Pendiente de pago
     * - PAID: Pagado
     */
    @Column
    private String status;

    /**
     * Numero de la boleta/factura
     */
    @Column
    private String documentNumber;

    /**
     * Fecha de emision
     */
    @Column
    private Date dateOfEmission;

    @ManyToOne
    @JoinColumn(name = "client_rut", referencedColumnName = "rut", nullable = false)
    private ClientEntity client;

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private WaterMeterEntity waterMeter;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private Set<InvoiceDetail> detail;
}
