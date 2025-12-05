package com.hardnets.coop.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
	private Long id;
	private SalesDocumentStatusEnum status;
	private String documentNumber;
	private Date dateOfEmission;

	@JsonBackReference
	private ClientDTO client;

	// TODO crear InvoiceDetailDTO
	@JsonBackReference
	private Object detail;

	@JsonBackReference
	private WaterMeterDTO waterMeter;

}
