package com.hardnets.coop.model.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.hardnets.coop.model.entity.ClientTypeEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeConsumptionDetailDto {
	/**
	 * Serial number of water meter
	 */
	private String serial;
	private String dni;
	private String fullName;
	private String clientType;
	private Integer lastRecord;
	private Integer actualRecord;
	private Long amountToPaid;
	private Long consumptionId;
	private List<DetailItemDto> detail = new ArrayList<>();

	public ResumeConsumptionDetailDto(String serial, String dni, ClientTypeEntity clientType, String fullName,
			Integer actualRecord, Long consumptionId) {
		this.serial = serial;
		this.dni = dni;
		this.actualRecord = actualRecord;
		this.consumptionId = consumptionId;
		this.fullName = fullName;
		this.clientType = clientType.getDescription();
	}
}
