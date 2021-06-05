package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "invoice_detail")
public class InvoiceDetailEntity extends SalesDocumentDetailEntity {

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false, referencedColumnName = "id")
    private InvoiceEntity invoice;

}
