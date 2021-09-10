package com.hardnets.coop.controller;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.dto.response.RelatedWaterMetersDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.service.WaterMeterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@Log4j2
@Api("All client operations")
@AllArgsConstructor
@RestController
public class ClientController {

    private final ClientService clientService;
    private final WaterMeterService waterMeterService;
    private final ModelMapper modelMapper;

    @GetMapping("/v1/client")
    public ResponseEntity<Collection<ClientDto>> getUsers(@RequestParam(required = false) String rut,
                                                          @RequestParam(required = false) String name) {
        FilterDto filter = new FilterDto();
        filter.setRut(rut);
        filter.setName(name);
        return ResponseEntity.ok(clientService.getUsers(filter));
    }

    @GetMapping("/v1/client/{rut}")
    public ResponseEntity<ClientDto> getUsers(@PathVariable String rut) {
        ClientEntity client = clientService.getByRut(rut).orElseThrow(ClientNotFoundException::new);
        return ResponseEntity.ok(convertToDto(client));
    }

    @PostMapping("/v1/client")
    public ResponseEntity<ClientDto> createUser(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.create(client), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an specific user", notes = "The primary key is rut, for example: '12345678-9'")
    @PutMapping("/v1/client")
    public ResponseEntity<ClientDto> updateUser(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.update(client), HttpStatus.OK);
    }

    @PostMapping("/v1/client/water-meter/{rut}")
    public ResponseEntity<Boolean> addWaterMeter(@RequestBody WaterMeterDto waterMeterDto,
                                                 @PathVariable String rut) {
        return new ResponseEntity<>(waterMeterService.relateToClient(waterMeterDto, rut), HttpStatus.CREATED);
    }

    @GetMapping("/v1/client/water-meter/{rut}")
    public ResponseEntity<Collection<RelatedWaterMetersDto>> getWaterMeters(@PathVariable String rut) {
        return ResponseEntity.ok(waterMeterService.getByUser(rut));
    }

    private ClientDto convertToDto(ClientEntity clientEntity) {
        return modelMapper.map(clientEntity, ClientDto.class);
    }

}
