package com.hardnets.coop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Getter
@Setter
@Entity
@Table(name = "watermeters")
public class WaterMeterEntity extends BaseEntity {

    /**
     * The format could be A2311
     */
    @Column
    private String number;

    @Column(nullable = false)
    private String trademark;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private DropDownListEntity size;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private DropDownListEntity status;

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.LAZY)
    private Set<InvoiceEntity> invoices;

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<SubsidyEntity> subsidies;

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ConsumptionEntity> consumptions;


}
