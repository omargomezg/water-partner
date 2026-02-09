package com.hardnets.coop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hardnets.coop.model.dto.DashboardRecord;

@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {

    @GetMapping
    public ResponseEntity<DashboardRecord> getDashboard() {
        return ResponseEntity.ok(new DashboardRecord(1450L, 15L, 45L));
    }

}
