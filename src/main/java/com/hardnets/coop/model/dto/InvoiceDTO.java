package com.hardnets.coop.model.dto;

import java.util.Date;

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
	private ClientDTO client;
	// TODO crear InvoiceDetailDTO
	private Object detail;
	private WaterMeterDTO waterMeter;

}
