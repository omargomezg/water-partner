package com.hardnets.coop.controller;

import com.hardnets.coop.model.dto.bulk.BulkWaterMeterUserDto;
import com.hardnets.coop.service.BulkService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@Api("All client operations")
@AllArgsConstructor
@RequestMapping("/bulk")
@RestController
public class BulkController {

    private final BulkService bulkService;

    @PostMapping(value = "/water-meter-with-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addWaterMeterWithUser(@RequestBody @Valid List<BulkWaterMeterUserDto> records) {
        bulkService.addWaterMeterWithUser(records);
        return ResponseEntity.ok().build();
    }
}
