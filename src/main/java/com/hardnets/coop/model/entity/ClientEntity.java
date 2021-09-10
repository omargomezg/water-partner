package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.dto.ClientDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "clients")
public class ClientEntity extends PersonEntity {

    @Column
    private Date dateOfAdmission;

    @Column
    private String telephone;

    @Column
    private String businessName = "";

    @Column
    private String businessActivity = "";

    private String profession;

    @Enumerated(EnumType.STRING)
    private ClientTypeEnum clientType;

    @ManyToOne
    @JoinColumn(name = "subsidy_id")
    private SubsidyEntity subsidy;

    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY)
    private Set<BillEntity> bills = new HashSet<>();

    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.EAGER)
    private Set<InvoiceEntity> invoices = new HashSet<>();

    @OneToMany(
            mappedBy = "client", fetch = FetchType.EAGER)
    private Set<WaterMeterEntity> waterMeter = new HashSet<>();

    public ClientEntity(ClientDto client) {
        super(client.getRut(), client.getNames(), client.getMiddleName(), client.getLastName(), client.getEmail(), client.getBirthDate());
        setDateOfAdmission(client.getDateOfAdmission());
        setTelephone(client.getTelephone());
        setBusinessActivity(client.getBusinessActivity());
        setBusinessName(client.getBusinessName());
        setProfession(client.getProfession());
    }

    public String getFullName() {
        String fullName;
        if (this.clientType.equals(ClientTypeEnum.PARTNER)) {
            fullName = String.format("%s %s %s", this.getNames(), this.getMiddleName(), this.getLastName());
        } else {
            fullName = this.getBusinessName();
        }
        return fullName;
    }
}
