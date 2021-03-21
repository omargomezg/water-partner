package com.hardnets.coop.controller;

import com.hardnets.coop.dto.ClientDto;
import com.hardnets.coop.dto.WaterMeterDto;
import com.hardnets.coop.service.PersonService;
import com.hardnets.coop.service.WaterMeterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@Log4j2
@Api("All client operations")
@RestController
public class ClientController {

    private final PersonService<ClientDto, ClientDto> personService;
    private final WaterMeterService waterMeterService;


    @Autowired
    ClientController(@Qualifier("clientService") PersonService<ClientDto, ClientDto> personService,
                     WaterMeterService waterMeterService) {
        this.personService = personService;
        this.waterMeterService = waterMeterService;
    }

    @GetMapping("/v1/client")
    public ResponseEntity<Collection<ClientDto>> getUsers() {
        log.info("access to Get Users");
        return ResponseEntity.ok(personService.getUsers());
    }

    @GetMapping("/v1/client/{rut}")
    public ResponseEntity<ClientDto> getUsers(@PathVariable String rut) {
        log.info("access to Get User rut {}", rut);
        ClientDto client = personService.getByRut(rut);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/v1/client")
    public ResponseEntity<ClientDto> createUser(@RequestBody @Valid ClientDto client) throws Exception {
        log.info("access to Post Client");
        return new ResponseEntity<>(personService.create(client), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an specific user", notes = "The primary key is rut, for example: '12345678-9'")
    @PutMapping("/v1/client")
    public ResponseEntity<ClientDto> updateUser(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(personService.update(client), HttpStatus.OK);
    }

    @PostMapping("/v1/client/water-meter/{rut}")
    public ResponseEntity<Boolean> addWaterMeter(@RequestBody WaterMeterDto waterMeterDto,
                                                 @PathVariable String rut) {
        return new ResponseEntity<>(waterMeterService.relateToClient(waterMeterDto, rut), HttpStatus.CREATED);
    }

    @GetMapping("/v1/client/water-meter/{rut}")
    public ResponseEntity<Collection<WaterMeterDto>> getWaterMeters(@PathVariable String rut) {
        return new ResponseEntity<>(waterMeterService.getByUser(rut), HttpStatus.OK);
    }


}
