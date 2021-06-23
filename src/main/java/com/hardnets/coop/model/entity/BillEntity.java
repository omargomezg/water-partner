package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "bill")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BillEntity extends SalesDocumentEntity {

    @ManyToOne
    @JoinColumn(name = "client_rut", referencedColumnName = "rut", nullable = false)
    private ClientEntity client;

    @OneToMany(mappedBy = "bill")
    private Set<BillDetailEntity> detail = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private WaterMeterEntity waterMeter;

    @OneToOne
    @JoinColumn(name = "integration_id", referencedColumnName = "id")
    private LibreDteEntity integration = new LibreDteEntity();
}
