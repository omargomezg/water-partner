package com.hardnets.coop.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@Table(name = "consumption")
public class ConsumptionEntity extends BaseEntity {

    @Column
    private Integer reading = 0;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    private Date readingDate;

    // TODO esta deprecado? este campo aparece en PeriodEntity
    @Column
    private Date cutoffDate;

    @ManyToOne
    @JoinColumn(name = "watermeter_id", nullable = false)
    private WaterMeterEntity waterMeter;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private PeriodEntity period;

}
