package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "watermeters")
public class WaterMeterEntity extends BaseEntity {

    /**
     * Serial number
     */
    @Column
    private String serial;

    @Column(nullable = false)
    private String trademark = "";

    @Column
    private String sector;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Column
    private String description;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "client_rut", referencedColumnName = "rut")
    private ClientEntity client;

    @Enumerated(EnumType.STRING)
    private DiameterEnum diameter;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER)
    private Set<InvoiceEntity> invoices = new HashSet<>();

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER)
    private Set<BillEntity> bills = new HashSet<>();

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<SubsidyEntity> subsidies = new HashSet<>();

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ConsumptionEntity> consumptions = new HashSet<>();


}
