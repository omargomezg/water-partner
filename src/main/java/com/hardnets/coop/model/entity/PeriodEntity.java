package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.PeriodStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This indicate mensual consumption with start and end date
 */
@Getter
@Setter
@Entity
@Table(name = "period")
public class PeriodEntity extends BaseEntity {

    /**
     * Fecha de apertura del periodo
     */
    @NotNull
    @Column(nullable = false)
    private Date startDate;

    /**
     * Indica la fecha de cierre del periodo
     */
    @Column
    private Date endDate;

    /**
     * Indica si el proceso de creación de boletas se ha ejecutado junto con
     * el calculo de cada uno de los items
     */
    @Column
    private Boolean billsCreated = false;

    /**
     * Indica si el proceso de creación de factura y el calculo de cobros se ha realizado
     */
    @Column
    private Boolean invoicesCreated = false;

    /**
     * Indica el estado de un periodo el cual puede ser:
     * - ACTIVE: Abierto, por cuanto puede recibir lecturas para el mes
     * - CLOSED: Cerrado, cuando cambia a este estado se generan las boletas/facturas
     */
    @Column
    @Enumerated(EnumType.STRING)
    private PeriodStatusEnum status = PeriodStatusEnum.PREPARED;

    /**
     * Relación de consumos para un periodo
     */
    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<ConsumptionEntity> consumptions = new HashSet<>();
}
