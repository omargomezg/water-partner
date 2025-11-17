package com.hardnets.coop.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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
