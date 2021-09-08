package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
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

    @Column
    private Date cutoffDate;

    @ManyToOne
    @JoinColumn(name = "watermeter_id", nullable = false)
    private WaterMeterEntity waterMeter;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private PeriodEntity period;

}
