package com.hardnets.coop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardnets.coop.model.entity.SectorEntity;
import com.hardnets.coop.service.SectorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.hardnets.coop.model.dto.SectorDTO;
import org.modelmapper.ModelMapper;

@RequiredArgsConstructor
@RequestMapping("/v1/sector")
@RestController
public class SectorController {

	private final SectorService sectorService;
	private final ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<SectorEntity>> getSectors() {
		return ResponseEntity.ok(sectorService.findAll());
	}

	@PostMapping
	public ResponseEntity<SectorDTO> createSector(@RequestBody @Valid SectorDTO sectorDto) {
		SectorEntity sector = modelMapper.map(sectorDto, SectorEntity.class);
		SectorEntity savedSector = sectorService.save(sector);
		return ResponseEntity.ok(modelMapper.map(savedSector, SectorDTO.class));
	}

	@PutMapping
	public ResponseEntity<SectorEntity> updateSector(@RequestBody @Valid SectorEntity sector) {
		return new ResponseEntity<SectorEntity>(sectorService.save(sector), HttpStatus.CREATED);
	}
}
