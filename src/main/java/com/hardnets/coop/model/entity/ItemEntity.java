package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.CalculationTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Conceptos a cobrar a un cliente el cual puede ser un valor fijo o calculado
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "item")
public class ItemEntity extends BaseEntity {

    /**
     * Descripción detallada del item a cobrar
     */
    private String description;

    /**
     * Descripción resumida del item a cobrar
     * aplica al detalle en la boleta/factura
     */
    private String excerpt;

    /**
     * En caso de cobrar un monto fijo se debe indicar en esta variable
     */
    private Long amount;

    /**
     * Es el método utilizado para realizar el calculo del item
     */
    @Column(name = "calculation_type")
    private CalculationTypeEnum methodOfCalculating;

    /**
     * Cuando el monto no requiere de calculo este valor debe ser true
     */
    @Column(name = "fixed_amount")
    private Boolean isFixedAmount;

    /**
     * Solo aplica cargo de este item si es activo
     */
    @Column(name = "status")
    private Boolean isActive;

    /**
     * Tipo de usuario al que aplica el item
     */
    @ManyToOne
    @JoinColumn(name = "client_type_id", referencedColumnName = "id")
    private DropDownListEntity assignedTo;
}
