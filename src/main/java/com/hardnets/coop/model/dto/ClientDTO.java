package com.hardnets.coop.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.model.constant.DniTypeEnum;
import com.hardnets.coop.model.constant.NationalityEnum;
import com.hardnets.coop.model.dto.views.AppViews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {

	@JsonView(AppViews.Internal.class)
	private String dni;

	@JsonView(AppViews.Internal.class)
	private DniTypeEnum typeOfDni;

	@Builder.Default
	@JsonView(AppViews.Internal.class)
	private NationalityEnum nationality = NationalityEnum.CHILEAN;

	@JsonView(AppViews.Internal.class)
	private String fullName;

	@JsonView(AppViews.Internal.class)
	private Date birthDate;

	@JsonView(AppViews.Internal.class)
	private String email;

	@JsonView(AppViews.Internal.class)
	private Boolean enabled;

	@JsonView(AppViews.Internal.class)
	private String password;

	@JsonView(AppViews.Internal.class)
	private Date dateOfAdmission;

	@JsonView(AppViews.Internal.class)
	private String telephone;

	@Builder.Default
	@JsonView(AppViews.Internal.class)
	private String businessActivity = "";

	@JsonView(AppViews.Internal.class)
	private String profession;

	@JsonView(AppViews.Internal.class)
	private Integer clientNumber;

	@JsonBackReference
	@JsonView(AppViews.Internal.class)
	private SectorDTO sector;

	@JsonView(AppViews.Internal.class)
	private ClientTypeDTO clientType;

	@JsonBackReference
	@JsonView(AppViews.Internal.class)
	private SubsidyDTO subsidy;

	@Builder.Default
	@JsonManagedReference
	@JsonView(AppViews.Internal.class)
	private Set<BillDTO> bills = new HashSet();

	@Builder.Default
	@JsonManagedReference
	@JsonView(AppViews.Internal.class)
	private Set<InvoiceDTO> invoices = new HashSet();

	@Builder.Default
	@JsonManagedReference
	@JsonView(AppViews.Internal.class)
	private Set<WaterMeterDTO> waterMeter = new HashSet();
}
