package com.hardnets.coop.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true, exclude = "bill")
@Data
@Entity
@Table(name = "bill_detail")
public class BillDetailEntity extends SalesDocumentDetailEntity {

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false, referencedColumnName = "id")
    private BillEntity bill;
}
