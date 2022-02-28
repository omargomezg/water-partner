package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "bill_detail")
public class BillDetailEntity extends SalesDocumentDetailEntity {

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false, referencedColumnName = "id")
    private BillEntity bill;

}
