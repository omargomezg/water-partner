package com.hardnets.coop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "subsidies")
public class SubsidyEntity extends BaseEntity {

    @Column(nullable = false)
    private Date startDate;

    @Column
    private Date endingDate;

    @Column(nullable = false)
    private Short percentaje;

    @JsonBackReference
    @OneToMany(mappedBy = "subsidy", fetch = FetchType.LAZY)
    private Set<ClientEntity> clients;

    @ManyToOne
    @JoinColumn(name = "watermeter_id")
    private WaterMeterEntity waterMeter;
}
