package com.hardnets.coop.controller;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.dto.ClientsDto;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;
import com.hardnets.coop.model.dto.request.FilterDto;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.service.impl.BillServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.ConversionService;
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


@Log4j2
@AllArgsConstructor
@RequestMapping("/v1/client")
@RestController
public class ClientController {

    private final ClientService clientService;
    private final BillServiceImpl bill;
    private final ConversionService conversionService;

    @GetMapping
    public ResponseEntity<ClientsDto> getUsers(@RequestParam(required = false) String dni,
                                               @RequestParam(required = false) String name,
                                               @RequestParam Integer pageIndex,
                                               @RequestParam Integer pageSize) {
        FilterDto filter = new FilterDto();
        filter.setDni(dni);
        filter.setName(name);
        return ResponseEntity.ok(clientService.getFilteredUsers(filter, pageIndex, pageSize));
    }

    @GetMapping("/{dni}")
    public ResponseEntity<ClientDto> getUsers(@PathVariable String dni) {
        var clientEntity = clientService.getByDni(dni).orElseThrow(ClientNotFoundException::new);
        return ResponseEntity.ok(conversionService.convert(clientEntity, ClientDto.class));
    }

    @PostMapping
    public ResponseEntity<ClientDto> createUser(@RequestBody @Valid ClientDto client) {
        log.info("Creating client with DNI: {}", client.getDni());
        return new ResponseEntity<>(clientService.create(client), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ClientDto> updateUser(@RequestBody @Valid ClientDto client) {
        return new ResponseEntity<>(clientService.update(client), HttpStatus.OK);
    }

    @GetMapping("/document")
    public ResponseEntity<IssuedBillsDto> getRelatedDocuments(@RequestParam String rut,
                                                              @RequestParam(defaultValue = "1") Integer status,
                                                              @RequestParam Integer pageIndex,
                                                              @RequestParam Integer pageSize) {
        SalesDocumentStatusEnum statusEnum = SalesDocumentStatusEnum.castIntToEnum(status);
        var documents = bill.getAllByStatusAndDni(statusEnum, rut, pageIndex, pageSize);
        return ResponseEntity.ok(documents);
    }

}
