package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@Data
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
    @OneToMany(mappedBy = "subsidy", fetch = FetchType.LAZY)
    private Set<ClientEntity> clients;

    @ManyToOne
    @JoinColumn(name = "watermeter_id")
    @JsonBackReference
    private WaterMeterEntity waterMeter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decree_id")
    private DecreeEntity decree;
}
