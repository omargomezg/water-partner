package com.hardnets.coop.controller;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.ClientsDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.model.dto.response.RelatedWaterMetersDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.service.impl.BillServiceImpl;
import com.hardnets.coop.service.impl.WaterMeterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

@Api("All client operations")
@AllArgsConstructor
@RequestMapping("/v1/client")
@RestController
public class ClientController {

    private final ClientService clientService;
    private final WaterMeterService waterMeterService;
    private final BillServiceImpl bill;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ClientsDto> getUsers(@RequestParam(required = false) String rut,
                                               @RequestParam(required = false) String name,
                                               @RequestParam Integer pageIndex,
                                               @RequestParam Integer pageSize) {
        FilterDto filter = new FilterDto();
        filter.setRut(rut);
        filter.setName(name);
        return ResponseEntity.ok(clientService.getFilteredUsers(filter, pageIndex, pageSize));
    }

    @GetMapping("/{rut}")
    public ResponseEntity<ClientDto> getUsers(@PathVariable String rut) {
        ClientEntity client = clientService.getByRut(rut).orElseThrow(ClientNotFoundException::new);
        return ResponseEntity.ok(convertToDto(client));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createUser(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.create(client), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an specific user", notes = "The primary key is rut, for example: '12345678-9'")
    @PutMapping
    public ResponseEntity<ClientDto> updateUser(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.update(client), HttpStatus.OK);
    }

    @PostMapping("/water-meter/{rut}")
    public ResponseEntity<Void> addWaterMeter(@RequestBody WaterMeterDto waterMeterDto,
                                              @PathVariable String rut) {
        waterMeterService.relateToClient(waterMeterDto, rut);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/water-meter/{rut}")
    public ResponseEntity<Collection<RelatedWaterMetersDto>> getWaterMeters(@PathVariable String rut) {
        return ResponseEntity.ok(waterMeterService.getByUser(rut));
    }

    @GetMapping("/document")
    public ResponseEntity<IssuedBillsDto> getRelatedDocuments(@RequestParam String rut,
                                                              @RequestParam(defaultValue = "1") Integer status,
                                                              @RequestParam Integer pageIndex,
                                                              @RequestParam Integer pageSize) {
        SalesDocumentStatusEnum statusEnum = SalesDocumentStatusEnum.castIntToEnum(status);
        var documents = bill.getAllByStatusAndRut(statusEnum, rut, pageIndex, pageSize);
        return ResponseEntity.ok(documents);
    }

    private ClientDto convertToDto(ClientEntity clientEntity) {
        return modelMapper.map(clientEntity, ClientDto.class);
    }

}
