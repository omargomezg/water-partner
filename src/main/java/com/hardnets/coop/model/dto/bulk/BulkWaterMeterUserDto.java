package com.hardnets.coop.model.dto.bulk;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BulkWaterMeterUserDto {

	@NotEmpty
	private String dni;

	@NotEmpty
	private String fullName;

	@NotEmpty
	@NotNull
	private Integer diameter;

	@NotNull
	@NotEmpty
	private Integer serial = 0;

	@NotNull
	@NotEmpty
	private Integer reading;

}
