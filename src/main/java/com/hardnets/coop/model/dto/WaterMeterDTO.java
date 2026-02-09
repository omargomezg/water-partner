package com.hardnets.coop.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.views.AppViews;
import com.hardnets.coop.model.entity.WaterMeterEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterMeterDTO {
	@JsonView(AppViews.Internal.class)
	private Long id;

	@JsonView(AppViews.Internal.class)
	private String serial;

	@JsonView(AppViews.Internal.class)
	private String trademark;

	@JsonView(AppViews.Internal.class)
	private DiameterEnum diameter;

	@JsonView(AppViews.Internal.class)
	private String comment;

	@JsonView(AppViews.Internal.class)
	private SectorDTO sector;

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

	@Builder.Default
	@JsonView(AppViews.Internal.class)
	private Integer flatFee = 0;

	@Builder.Default
	@JsonView(AppViews.Internal.class)
	private Float cubicMeter = 0f;

	/**
	 * Date when is related to
	 */
	@JsonView(AppViews.Internal.class)
	private Date dischargeDate;

	@JsonView(AppViews.Internal.class)
	private com.hardnets.coop.model.dto.response.SubsidyDto subsidy;

	public WaterMeterDTO(Long id, String serial, String trademark, DiameterEnum diameter, String comment,
			Date updatedAt) {
		this.id = id;
		this.serial = serial;
		this.trademark = trademark;
		this.diameter = diameter;
		this.comment = comment;
		this.updatedAt = updatedAt;
	}

	public WaterMeterDTO(WaterMeterEntity waterMeter) {
		this.setId(waterMeter.getId());
		this.setSerial(waterMeter.getSerial());
		this.setTrademark(waterMeter.getTrademark());
		this.setDischargeDate(waterMeter.getCreatedAt());
		this.setComment(waterMeter.getDescription());
		this.diameter = waterMeter.getDiameter();
		this.setUpdatedAt(waterMeter.getUpdatedAt());
	}
}
