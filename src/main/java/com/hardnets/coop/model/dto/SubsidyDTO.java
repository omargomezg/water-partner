package com.hardnets.coop.model.dto;

import java.util.Date;
import java.util.Set;

import com.hardnets.coop.model.entity.DecreeEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubsidyDTO {

	private Long id;
	private Date created;
	private Date updated;
	private Date startDate;
	private Date endingDate;
	private Short percentage;
	private String observation;
	private Boolean isActive;
	private Set<ClientDTO> clients;
	private WaterMeterEntity waterMeter;
	private DecreeEntity decree;

}
