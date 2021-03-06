package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.model.dto.ReadingsDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDto;
import com.hardnets.coop.model.dto.response.DetailItemDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDto;
import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.SalesDocumentDetailEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.ConsumptionRepositoryCrud;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ConsumptionService {

    private final ConsumptionRepository consumptionRepository;
    private final ConsumptionRepositoryCrud consumptionRepositoryCrud;
    private final WaterMeterRepository waterMeterRepository;
    private final PeriodRepository periodRepository;
    private final ClientRepository clientRepository;
    private final BillDetailService billDetailService;

    public List<ReadingsDto> findRecordsByWaterMeterId(Long id) {
        Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findById(id);
        if (waterMeter.isPresent()) {
            return consumptionRepository.findAllByWaterMeter(waterMeter.get().getId());
        }
        return new ArrayList<>();
    }

    public ConsumptionEntity create(WaterMeterEntity waterMeter, Integer consumption, PeriodEntity period) {
        ConsumptionEntity consumptionEntity = new ConsumptionEntity();
        consumptionEntity.setCreated(LocalDateTime.now());
        consumptionEntity.setReading(consumption);
        consumptionEntity.setReadingDate(new Date());
        consumptionEntity.setWaterMeter(waterMeter);
        consumptionEntity.setPeriod(period);
        return consumptionRepositoryCrud.save(consumptionEntity);
    }

    public ConsumptionEntity update(ConsumptionEntity consumptionEntity) {
        return consumptionRepository.save(consumptionEntity);
    }

    public Optional<ConsumptionEntity> findOneByPeriodAndWaterMeter(Long periodId, Long waterMeterId) {
        Optional<PeriodEntity> period = periodRepository.findById(periodId);
        Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findById(waterMeterId);
        if (period.isPresent() && waterMeter.isPresent()) {
            return consumptionRepository.findAllByPeriodAndWaterMeter(period.get().getId(),
                    waterMeter.get().getId());
        }
        return Optional.empty();
    }

    public ResumeConsumptionDto findAllByPeriodId(Long periodId, int pageIndex, int pageSize) {
        ResumeConsumptionDto response = new ResumeConsumptionDto();
        PeriodEntity period = periodRepository.findById(periodId).orElseThrow(PeriodException::new);
        Optional<PeriodEntity> lastPeriod = periodRepository.findFirstByIdNot(periodId);
        response.setStatus(period.getStatus().label);
        response.setStartDate(period.getStartDate());
        response.setEndDate(period.getEndDate());

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ResumeConsumptionDetailDto> page = consumptionRepository.findAllByPeriod(period.getId(), pageable);
        response.setDetail(page.getContent());
        response.getDetail().parallelStream().forEach(item -> {
            var lastRecord = getLastRecordConsumption(item.getSerial(), lastPeriod);
            item.setLastRecord(lastRecord);
            Optional<ConsumptionEntity> consumption = consumptionRepository.findById(item.getConsumptionId());
            if (consumption.isPresent()) {
                List<BillDetailEntity> billDetails = billDetailService.getDetail(consumption.get(), null);
                item.setAmountToPaid(billDetails.stream().mapToLong(SalesDocumentDetailEntity::getTotalAmount).sum());
                List<DetailItemDto> detail = billDetails.stream().map(billDetail -> new DetailItemDto(billDetail.getConcept(), billDetail.getTotalAmount())).collect(Collectors.toList());
                item.getDetail().addAll(detail);
            }
        });
        response.setTotalHits(page.getTotalElements());
        return response;
    }

    /**
     * Obtiene el ultimo valor de consumo registrado al medidor
     *
     * @param serial Numero de serie del medidor
     * @param period Periodo a consultar
     * @return Ultimo registro de consumo del medidor consultado
     */
    private Integer getLastRecordConsumption(Integer serial, Optional<PeriodEntity> period) {
        Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findBySerial(serial);
        if (period.isPresent() && waterMeter.isPresent()) {
            Optional<ConsumptionEntity> consumption =
                    consumptionRepository.findAllByPeriodAndWaterMeter(period.get().getId(),
                            waterMeter.get().getId());
            return consumption.isPresent() ? consumption.get().getReading() : 0;
        }
        return 0;
    }

    public ConsumptionClientDto findAllByClient(String rut, Integer pageIndex, Integer pageSize) {
        ClientEntity client = clientRepository.findByDni(rut).orElseThrow(() -> new ClientNotFoundException(rut));
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ConsumptionClientDetailDto> page = consumptionRepository.findAllByClient(rut, pageable);
        ConsumptionClientDto pageDto = new ConsumptionClientDto();
        pageDto.setFullName(client.getFullName());
        pageDto.setTotalHits(1L);
        pageDto.setContent(page.getContent());
        pageDto.getContent().forEach(item -> {

        });
        return pageDto;
    }

    /**
     * Crea todos los registros de consumos para tener una base en futuras consultas
     * Se crea un registro por cada medidor relacionado a un cliente
     *
     * @param periodId el Id del periodo con el cual se crear??n las lecturas con valor 0
     */
    @Async
    @Transactional
    public void createAllRecords(Long periodId) {
        periodRepository.findById(periodId).ifPresent(period ->
                waterMeterRepository.findAll().parallelStream().forEach(waterMeter -> {
                    ConsumptionEntity consumptionEntity = new ConsumptionEntity();
                    consumptionEntity.setReading(0);
                    consumptionEntity.setPeriod(period);
                    consumptionEntity.setWaterMeter(waterMeter);
                    consumptionRepository.save(consumptionEntity);
                }));
    }
}
