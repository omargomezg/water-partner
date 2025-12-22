package com.hardnets.coop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.mapper.ClientDTOAssembler;
import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.dto.ClientDTO;
import com.hardnets.coop.model.dto.ClientFilterRequest;
import com.hardnets.coop.model.dto.ClientRequestDTO;
import com.hardnets.coop.model.dto.PageResponse;
import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillsDto;
import com.hardnets.coop.model.dto.views.AppViews;
import com.hardnets.coop.model.dto.views.ViewSerializer;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.service.impl.BillServiceImpl;
import com.hardnets.coop.service.impl.WaterMeterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor
@RequestMapping("/v1/client")
@RestController
public class ClientController {

	private final ClientService clientService;
	private final WaterMeterService waterMeterService;
	private final BillServiceImpl bill;
	private final ConversionService conversionService;
	private final ClientDTOAssembler clientDTOAssembler;
	private final ModelMapper modelMapper;

	@GetMapping
	@JsonView(AppViews.Internal.class)
	@ViewSerializer(value = { AppViews.Admin.class, AppViews.Internal.class })
	public ResponseEntity<PageResponse<ClientDTO>> getUsers(ClientFilterRequest params) {
		var clients = clientService.getFilteredUsers(params);
		var totalOfElements = clientService.getTotalOfFilteredUsers(params);
		var response = new PageResponse<ClientDTO>();
		response.setContent(clients.stream().map(clientDTOAssembler::toModel).toList());
		response.setTotalElements(totalOfElements);
		response.getContent().forEach(c -> log.info("Para {}, os Medidores: {}", c.getDni(), c.getWaterMeters()));
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{dni}")
	@JsonView(AppViews.Admin.class)
	@ViewSerializer(value = { AppViews.Admin.class, AppViews.Internal.class })
	public ResponseEntity<ClientDTO> getUsers(@PathVariable String dni) {
		var clientEntity = clientService.getByDni(dni).orElseThrow(ClientNotFoundException::new);
		return ResponseEntity.ok(conversionService.convert(clientEntity, ClientDTO.class));
	}

	/**
	 * Agrega un medidor a un cliente
	 * 
	 * @param dni        Identificador del cliente
	 * @param waterMeter Medidor a agregar
	 * @return Cliente con el medidor agregado
	 */
	@PostMapping("/{dni}/water-meter")
	public ResponseEntity<ClientDTO> addWaterMeterToClient(@PathVariable String dni,
			@RequestBody WaterMeterDTO waterMeter) {
		var client = clientService.getByDni(dni).orElseThrow(ClientNotFoundException::new);
		var meter = waterMeterService.getById(waterMeter.getId());
		client = clientService.addWaterMeter(client, meter);
		var clientDTO = clientDTOAssembler.toModel(client);
		return new ResponseEntity<ClientDTO>(clientDTO, HttpStatus.CREATED);
	}

	/**
	 * Remueve un medidor de un cliente
	 * 
	 * @param dni     Identificador del cliente
	 * @param meterId Identificador del medidor a remover
	 * @return Cliente con el medidor removido
	 */
	@DeleteMapping("/{dni}/water-meter/{meterId}")
	public ResponseEntity<ClientDTO> removeWaterMeterFromClient(@PathVariable String dni,
			@PathVariable Long meterId) {
		var client = clientService.getByDni(dni).orElseThrow(ClientNotFoundException::new);
		var meter = waterMeterService.getById(meterId);
		client = clientService.removeWaterMeter(client, meter);
		var clientDTO = clientDTOAssembler.toModel(client);
		return new ResponseEntity<ClientDTO>(clientDTO, HttpStatus.OK);
	}

	@PostMapping
	@JsonView(AppViews.Admin.class)
	@ViewSerializer(value = { AppViews.Admin.class, AppViews.Internal.class })
	public ResponseEntity<ClientDTO> createUser(@RequestBody @Valid ClientRequestDTO client) {
		log.info("Creating client with DNI: {}", client.getDni());
		var result = clientService.create(client);
		return new ResponseEntity<>(modelMapper.map(result, ClientDTO.class), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<ClientDTO> updateUser(@RequestBody @Valid ClientRequestDTO client) {
		var result = clientService.update(client);
		return ResponseEntity.ok(modelMapper.map(result, ClientDTO.class));
	}

	@DeleteMapping("/{dni}")
	public ResponseEntity<Void> deleteClient(@PathVariable String dni) {
		clientService.deleteByDni(dni);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/document")
	public ResponseEntity<IssuedBillsDto> getRelatedDocuments(@RequestParam String rut,
			@RequestParam(defaultValue = "1") Integer status, @RequestParam Integer pageIndex,
			@RequestParam Integer pageSize) {
		SalesDocumentStatusEnum statusEnum = SalesDocumentStatusEnum.castIntToEnum(status);
		var documents = bill.getAllByStatusAndDni(statusEnum, rut, pageIndex, pageSize);
		return ResponseEntity.ok(documents);
	}

}
