package com.hardnets.coop.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.views.AppViews;
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
	@JsonView(AppViews.Internal.class)
	private Long id;

	@JsonView(AppViews.Internal.class)
	private Integer serial;

	@JsonView(AppViews.Internal.class)
	private String trademark;

	@JsonView(AppViews.Internal.class)
	private DiameterEnum diameter;

	@JsonView(AppViews.Internal.class)
	private String comment;

	@JsonView(AppViews.Internal.class)
	private String sector;

	@JsonView(AppViews.Internal.class)
	private Date createdAt;

	@JsonView(AppViews.Internal.class)
	private Date updatedAt;

	@JsonView(AppViews.Internal.class)
	private String dni;

	@JsonView(AppViews.Internal.class)
    private String description;

	@JsonView(AppViews.Internal.class)
    private StatusEnum status;

	@JsonView(AppViews.Internal.class)
	private Integer flatFee;

	@JsonView(AppViews.Internal.class)
	private Float cubicMeter;

	/**
	 * Date when is related to
	 */
	@JsonView(AppViews.Internal.class)
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
