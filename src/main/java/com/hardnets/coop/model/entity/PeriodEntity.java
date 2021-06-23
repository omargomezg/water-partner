package com.hardnets.coop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
     * Es el numero de mes de consumo en el periodo
     */
    @Column
    private Byte currentMonth;

    /**
     * Indica el estado de un periodo el cual puede ser:
     * - ACTIVE: Abierto, por cuanto puede recibir lecturas para el mes
     * - CLOSED: Cerrado, cuando cambia a este estado se generan las boletas/facturas
     */
    @Column
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "period", fetch = FetchType.LAZY)
    private Set<ConsumptionEntity> consumptions;

    public PeriodEntity() {
        setConsumptions(new HashSet<>());
    }
}
