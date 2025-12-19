package com.hardnets.coop.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.hardnets.coop.model.dto.PageResponse;
import com.hardnets.coop.model.dto.PeriodFilterRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.service.PeriodService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/period")
public class PeriodController {

	private final PeriodService periodService;
	private final ModelMapper modelMapper;

	/**
	 * Listado de periodo
	 *
	 * @return una lista de periodo
	 */
	@GetMapping
	public ResponseEntity<PageResponse<PeriodDto>> list(PeriodFilterRequest filter) {
		var periods = periodService.findAll(filter);
		var total = periodService.totalElements(filter);
		var result = new  PageResponse<PeriodDto>();
		result.setContent(periods.stream().map(p -> modelMapper.map(p, PeriodDto.class)).toList());
		result.setTotalElements(total);
		return ResponseEntity.ok(result);
	}

	/**
	 * Crea un periodo
	 *
	 * @return El periodo creado
	 */
	@PostMapping
	public ResponseEntity<PeriodDto> create(@RequestBody PeriodDto periodDto) {
		var result = periodService.create(periodDto);
		return ResponseEntity.ok(result);
	}

	/**
	 * Actualiza un periodo
	 *
	 * @return El periodo actualizado
	 */
	@PutMapping("/{id}")
	public ResponseEntity<PeriodDto> update(@PathVariable Long id, @RequestBody PeriodDto periodDto) {
		var result = periodService.update(periodDto);
		return ResponseEntity.ok(result);
	}

}
