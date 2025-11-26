package com.hardnets.coop.controller;

import com.hardnets.coop.model.entity.SectorEntity;
import com.hardnets.coop.service.SectorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/sector")
@RestController
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<SectorEntity>> getSectors() {
        return ResponseEntity.ok(sectorService.findAll());
    }

    @PostMapping
    public ResponseEntity<SectorEntity> createSector(@RequestBody @Valid SectorEntity sector) {
        return ResponseEntity.ok(sectorService.save(sector));
    }

    @PutMapping
    public ResponseEntity<SectorEntity> updateSector(@RequestBody @Valid SectorEntity sector) {
        return new ResponseEntity(sectorService.save(sector), HttpStatus.CREATED);
    }
}
