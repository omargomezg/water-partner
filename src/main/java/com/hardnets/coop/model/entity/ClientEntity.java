package com.hardnets.coop.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hardnets.coop.model.dto.ClientDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "client")
public class ClientEntity extends PersonEntity {

	private Date dateOfAdmission;

	@Column(length = 15)
	private String telephone;

	@Column
	private String businessActivity = "";

	private String profession;

	private Integer clientNumber;

	@ManyToOne
	@JoinColumn(name = "sector_id")
	private SectorEntity sector;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_type_id")
	private ClientTypeEntity clientType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subsidy_id")
	private SubsidyEntity subsidy;

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	@ToString.Exclude
	private Set<BillEntity> bills = new HashSet<>();

	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private Set<InvoiceEntity> invoices = new HashSet<>();

	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private Set<WaterMeterEntity> waterMeter = new HashSet<>();

	public ClientEntity(ClientDto client) {
		super(client.getDni(), client.getFullName(), client.getEmail(), client.getBirthDate());
		setDateOfAdmission(client.getDateOfAdmission());
		setTelephone(client.getTelephone());
		setBusinessActivity(client.getBusinessActivity());
		setFullName(client.getFullName());
		setProfession(client.getProfession());
	}
}
