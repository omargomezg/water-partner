package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "invoice")
@EqualsAndHashCode(callSuper = true)
public class InvoiceEntity extends SalesDocumentEntity {


    @ManyToOne
    @JoinColumn(name = "client_dni", referencedColumnName = "dni", nullable = false)
    private ClientEntity client;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
    private Set<InvoiceDetailEntity> detail = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private WaterMeterEntity waterMeter;
}
