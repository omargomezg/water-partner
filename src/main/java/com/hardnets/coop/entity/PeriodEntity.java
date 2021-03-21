package com.hardnets.coop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This indicate mensual consumption with start and end date
 */
@Getter
@Setter
@Entity
@Table(name = "periods")
public class PeriodEntity extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private Date startDate;

    @Column
    private Date endDate;

    @Column
    private String status;

    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY)
    private Set<ConsumptionEntity> consumptions;

    public PeriodEntity() {
        setConsumptions(new HashSet<>());
    }
}
