package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "invoice_detail")
public class InvoiceDetail extends BaseEntity {
    @Column
    private String concept;
    @Column
    private Long baseAmount;
    @Column
    private Long totalAmount;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceEntity invoice;
}
