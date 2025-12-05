package com.hardnets.coop.model.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "client")
@EqualsAndHashCode(callSuper = true)
public class ClientEntity extends PersonEntity {

	private Date dateOfAdmission;

	@Column(length = 15)
	private String telephone;

	@Column
	@Builder.Default
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

	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
	private Set<BillEntity> bills = new HashSet<>();

	@Builder.Default
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private Set<InvoiceEntity> invoices = new HashSet<>();

	@Builder.Default
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private Set<WaterMeterEntity> waterMeters = new HashSet<>();

}
