package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.CalculationTypeEnum;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Conceptos a cobrar a un cliente el cual puede ser un valor fijo o calculado
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
@Table(name = "item")
@NoArgsConstructor
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
    private Integer amount;

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
    @Column(name = "client_type")
    private ClientTypeEnum assignedTo;
}
