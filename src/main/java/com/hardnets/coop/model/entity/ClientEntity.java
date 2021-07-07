package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "clients")
public class ClientEntity extends PersonEntity {

    @Column
    private Date dateOfAdmission;

    @Column
    private String telephone;

    @Column
    private String businessName;

    @Column
    private String businessActivity;

    private String profession;

    @ManyToOne
    @JoinColumn(name = "clienttype_id")
    private DropDownListEntity clientType;

    @ManyToOne
    @JoinColumn(name = "subsidy_id")
    private SubsidyEntity subsidy;

    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY)
    private Set<BillEntity> bills;

    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY)
    private Set<InvoiceEntity> invoices;

    @OneToMany(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<WaterMeterEntity> waterMeter = new HashSet<>();

    public String getFullName() {
        String fullName;
        if (this.clientType.getCode().equals("PARTNER")) {
            fullName = String.format("%s %s %s", this.getNames(), this.getMiddleName(), this.getLastName());
        } else {
            fullName = this.getBusinessName();
        }
        return fullName;
    }
}
