package com.hardnets.coop.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardnets.coop.model.dto.ClientTypeDTO;
import com.hardnets.coop.model.entity.ClientTypeEntity;
import com.hardnets.coop.repository.ClientTypeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/v1/client-type")
@RestController
public class ClientTypeController {

	private final ClientTypeRepository clientTypeRepository;
	private final ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<ClientTypeDTO>> getClientTypes() {
		var clientTypeEntity = clientTypeRepository.findAll();
		return ResponseEntity
				.ok(clientTypeEntity.stream().map(ct -> modelMapper.map(ct, ClientTypeDTO.class)).toList());
	}

	@PostMapping
	public ResponseEntity<ClientTypeDTO> createClientType(@RequestBody ClientTypeDTO clientTypeDto) {
		var clientTypeEntity = new ClientTypeEntity();
		clientTypeEntity.setDescription(clientTypeDto.getDescription());
		var entity = clientTypeRepository.save(clientTypeEntity);
		var dto = modelMapper.map(entity, ClientTypeDTO.class);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientTypeDTO> updateClientType(@RequestBody ClientTypeDTO clientTypeDto,
			@PathVariable @NonNull Long id) {
		var clientTypeEntity = clientTypeRepository.findById(id).orElseThrow();
		clientTypeEntity.setDescription(clientTypeDto.getDescription());
		var entity = clientTypeRepository.save(clientTypeEntity);
		var dto = modelMapper.map(entity, ClientTypeDTO.class);
		return ResponseEntity.ok(dto);
	}

}
