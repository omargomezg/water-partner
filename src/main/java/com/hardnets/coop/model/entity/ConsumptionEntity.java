package com.hardnets.coop.model.entity;

import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "consumption")
@EqualsAndHashCode(callSuper = true)
public class ConsumptionEntity extends BaseEntity {

    @Column
    @Builder.Default
    private Integer reading = 0;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    @Column
    private Date readingDate;

    @ManyToOne
    @JoinColumn(name = "watermeter_id", nullable = false)
    private WaterMeterEntity waterMeter;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private PeriodEntity period;

}
