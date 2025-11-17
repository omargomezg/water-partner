package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "watermeter")
public class WaterMeterEntity extends BaseEntity {

    /**
     * Serial number
     */
    @Column(unique = true)
    private Integer serial;

    @Column(nullable = false)
    private String trademark = "";

    @Column
    private String sector;

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Column
    private String description = "";

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "client_dni", referencedColumnName = "dni")
    private ClientEntity client;

    @Enumerated(EnumType.STRING)
    private DiameterEnum diameter;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER,  orphanRemoval = true)
    private Set<InvoiceEntity> invoices = new HashSet<>();

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER,  orphanRemoval = true)
    private Set<BillEntity> bills = new HashSet<>();

    @OneToMany(mappedBy = "waterMeter",  orphanRemoval = true)
    @JsonManagedReference
    private Set<SubsidyEntity> subsidies = new HashSet<>();

    @OneToMany(mappedBy = "waterMeter", fetch = FetchType.EAGER,  orphanRemoval = true)
    @JsonManagedReference
    private Set<ConsumptionEntity> consumptions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WaterMeterEntity that = (WaterMeterEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
