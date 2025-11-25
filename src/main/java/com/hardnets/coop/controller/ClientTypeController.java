package com.hardnets.coop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardnets.coop.model.dto.clientType.ClientTypeDto;
import com.hardnets.coop.model.entity.ClientTypeEntity;
import com.hardnets.coop.repository.ClientTypeRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/v1/client-type")
@RestController
public class ClientTypeController {

    private final ClientTypeRepository clientTypeRepository;

    @GetMapping
    public ResponseEntity<List<ClientTypeDto>> getClientTypes() {
        var clientTypeEntity = clientTypeRepository.findAll();
        return ResponseEntity.ok(clientTypeEntity.stream().map(entity -> {
            var dto = new ClientTypeDto();
            dto.setId(entity.getId());
            dto.setDescription(entity.getDescription());
            return dto;
        }).toList());
    }

    @PostMapping
    public ResponseEntity<ClientTypeDto> createClientType(@RequestBody ClientTypeDto clientTypeDto) {
        var clientTypeEntity = new ClientTypeEntity();
        clientTypeEntity.setDescription(clientTypeDto.getDescription());
        var newEntity = clientTypeRepository.save(clientTypeEntity);
        var dto = new ClientTypeDto();
        dto.setId(newEntity.getId());
        dto.setDescription(newEntity.getDescription());
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

}
