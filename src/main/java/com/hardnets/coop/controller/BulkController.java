package com.hardnets.coop.controller;

import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.bulk.BulkWaterMeterUserDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.service.ClientService;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.impl.ConsumptionService;
import com.hardnets.coop.service.impl.WaterMeterService;
import com.hardnets.coop.util.RutUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/bulk")
@RestController
public class BulkController {

    private final PeriodService periodService;
    private final ClientService clientService;
    private final WaterMeterService waterMeterService;
    private final ConsumptionService consumptionService;

    @PostMapping(value = "/water-meter-with-user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addWaterMeterWithUser(@RequestBody @Valid List<BulkWaterMeterUserDto> records) {
        periodService.findByStatus(PeriodStatusEnum.ACTIVE);
        records.parallelStream().forEach(this::saveClient);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/reading", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addReadings(@RequestBody @Valid List<BulkWaterMeterUserDto> records,
                                         @RequestParam Long period) {
        records.forEach(item -> saveReading(item, period));
        return ResponseEntity.ok().build();
    }

    private void saveReading(BulkWaterMeterUserDto bulkRecord, Long periodId) {
        if (Boolean.TRUE.equals(isSerial(bulkRecord.getSerial()))) {
            if (waterMeterService.existSerial(bulkRecord.getSerial())
                    && (bulkRecord.getReading() > 0 && bulkRecord.getReading() != null)) {
                String dni = clearRutFormat(bulkRecord.getDni());
                clientService.getByDni(dni).ifPresent(client -> {
                    if (waterMeterService.existSerial(bulkRecord.getSerial())) {
                        WaterMeterEntity waterMeter = waterMeterService.getBySerial(bulkRecord.getSerial());
                        PeriodEntity period = periodService.findById(periodId).orElseThrow(PeriodException::new);
                        consumptionService.create(waterMeter, bulkRecord.getReading(), period);
                    }
                });
            }
        }
    }

    private void saveWaterMeter(BulkWaterMeterUserDto bulkRecord) {
        String dni = clearRutFormat(bulkRecord.getDni());
        if (!waterMeterService.existSerial(bulkRecord.getSerial()) && isSerial(bulkRecord.getSerial())) {
            WaterMeterEntity waterMeter = new WaterMeterEntity();
            waterMeter.setSerial(bulkRecord.getSerial());
            waterMeter.setDiameter(DiameterEnum.castIntToEnum(bulkRecord.getDiameter()));
            waterMeter.setCreated(new Date());
            waterMeter.setStatus(StatusEnum.NEW);

            if (RutUtils.validateRut(dni)) {
                clientService.getByDni(dni)
                        .ifPresent(waterMeter::setClient);
            }
            waterMeterService.create(waterMeter);
        }
    }

    private boolean isSerial(Integer serial) {
        return serial != null && serial > 0;
    }

    private void saveClient(BulkWaterMeterUserDto bulkRecord) {
        String rut = clearRutFormat(bulkRecord.getDni());
        if (!clientService.exist(rut) && RutUtils.validateRut(rut)) {
            ClientEntity client = new ClientEntity();
            client.setDni(rut);
            client.setNames(bulkRecord.getNames());
            client.setClientType(ClientTypeEnum.PARTNER);
            clientService.create(client);
        }
        saveWaterMeter(bulkRecord);
    }

    private String clearRutFormat(String rut) {
        return rut.replace(".", "")
                .replace("-", "");
    }

}
