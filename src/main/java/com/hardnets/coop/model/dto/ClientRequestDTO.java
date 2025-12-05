package com.hardnets.coop.model.dto;

import com.hardnets.coop.model.constant.DniTypeEnum;

import lombok.Data;

@Data
public class ClientRequestDTO {
	private String dni;
	private DniTypeEnum typeOfDni;
	private String fullName;
	private String email;
	private String telephone;
	private ClientTypeDTO clientType;
	private Long sector;
}
