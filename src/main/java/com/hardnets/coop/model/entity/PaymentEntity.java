package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "payment")
public class PaymentEntity extends BaseEntity {
    /**
     * Token que identifica la transacción en Flow
     */
    private String token;

    /**
     * Número de orden de cobro Flow
     */
    private Long flowOrder;

    /**
     * Estado actual de la transacción la cual puede ser:
     * - 1 Pendiente de Pago
     * - 2 Pagada
     * - 3 Rechazada
     * - 4 Anulada
     */
    private Byte status;
}
