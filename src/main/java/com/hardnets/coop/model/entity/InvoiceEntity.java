package com.hardnets.coop.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "invoice")
@EqualsAndHashCode(callSuper = true)
public class InvoiceEntity extends SalesDocumentEntity {


    @ManyToOne
    @JoinColumn(name = "client_dni", referencedColumnName = "dni", nullable = false)
    private ClientEntity client;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<InvoiceDetailEntity> detail = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private WaterMeterEntity waterMeter;
}
