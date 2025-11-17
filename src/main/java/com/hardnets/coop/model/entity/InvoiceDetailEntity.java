package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "invoice_detail")
public class InvoiceDetailEntity extends SalesDocumentDetailEntity {

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false, referencedColumnName = "id")
    private InvoiceEntity invoice;

}
