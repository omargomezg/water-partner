package com.hardnets.coop.model.dto;

import lombok.Data;

@Data
public class ClientFilterRequest {

	private String dni;
	private String name;
	private Integer page = 0;
	private Integer size = 0;

}
