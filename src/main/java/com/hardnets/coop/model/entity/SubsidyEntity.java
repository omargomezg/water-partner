package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "subsidies")
public class SubsidyEntity extends BaseEntity {

    @CreationTimestamp
    private Date created;

    @UpdateTimestamp
    private Date updated;

    @Column(nullable = false)
    private Date startDate;

    @Column
    private Date endingDate;

    @Column(nullable = false)
    private Short percentage;

    @Column
    private String observation;

    @Column
    private Boolean isActive;

    @JsonBackReference
    @OneToMany(mappedBy = "subsidy", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ClientEntity> clients;

    @ManyToOne
    @JoinColumn(name = "watermeter_id")
    @JsonBackReference
    private WaterMeterEntity waterMeter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decree_id")
    private DecreeEntity decree;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SubsidyEntity that = (SubsidyEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
