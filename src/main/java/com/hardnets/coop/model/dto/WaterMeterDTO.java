package com.hardnets.coop.model.dto;

import java.util.Date;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.entity.WaterMeterEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterMeterDTO {
	private Long id;
	private Integer serial;
	private String trademark;
	private DiameterEnum diameter;
	private String comment;
	private String sector;
	private Date createdAt;
	private Date updatedAt;
	private String dni;

	/**
	 * Date when is related to
	 */
	private Date dischargeDate;

	public WaterMeterDTO(Long id, Integer serial, String trademark, DiameterEnum diameter, String comment,
			String sector, Date updatedAt) {
		this.id = id;
		this.serial = serial;
		this.trademark = trademark;
		this.diameter = diameter;
		this.comment = comment;
		this.sector = sector;
		this.updatedAt = updatedAt;
	}

	public WaterMeterDTO(WaterMeterEntity waterMeter) {
		this.setId(waterMeter.getId());
		this.setSerial(waterMeter.getSerial());
		this.setTrademark(waterMeter.getTrademark());
		this.setDischargeDate(waterMeter.getCreatedAt());
		this.setComment(waterMeter.getDescription());
		this.diameter = waterMeter.getDiameter();
		this.setSector(waterMeter.getSector());
		this.setUpdatedAt(waterMeter.getUpdatedAt());
	}
}
