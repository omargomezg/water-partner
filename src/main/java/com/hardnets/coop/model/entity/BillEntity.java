package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "bill")
@EqualsAndHashCode(callSuper = true)
public class BillEntity extends SalesDocumentEntity {

    @ManyToOne
    @JoinColumn(name = "client_dni", referencedColumnName = "dni", nullable = false)
    private ClientEntity client;

    @OneToMany(mappedBy = "bill", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<BillDetailEntity> detail = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private WaterMeterEntity waterMeter;
}
