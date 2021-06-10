package com.hardnets.coop.service;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.model.dto.ReadingsDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDto;
import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.SalesDocumentDetailEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.impl.BillDetailService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
@AllArgsConstructor
@Service
public class ConsumptionService {

    public static final String ACTIVO = "ACTIVO";
    private final ConsumptionRepository consumptionRepository;
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

    public void create(Long id, Long consumption, PeriodEntity period) {
        WaterMeterEntity waterMeter = waterMeterRepository.findById(id).orElseThrow(() -> new WaterMeterNotFoundException("Water meter number " + id + " was " +
                "not" + " found"));
        ConsumptionEntity consumptionEntity = new ConsumptionEntity();
        consumptionEntity.setCreated(LocalDateTime.now());
        consumptionEntity.setConsumption(consumption);
        consumptionEntity.setReadingDate(new Date());
        consumptionEntity.setWaterMeter(waterMeter);
        consumptionEntity.setPeriod(period);
        consumptionRepository.save(consumptionEntity);
    }

    public void update(ConsumptionEntity consumptionEntity) {
        consumptionRepository.save(consumptionEntity);
    }

    public Optional<ConsumptionEntity> findOneByPeriodAndWaterMeter(Long periodId, Long waterMeterId) {
        Optional<PeriodEntity> period = periodRepository.findById(periodId);
        Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findById(waterMeterId);
        if (period.isPresent() && waterMeter.isPresent()) {
            return Optional.of(consumptionRepository.findAllByPeriodAndWaterMeter(period.get(), waterMeter.get()));
        }
        return Optional.empty();
    }

    public ResumeConsumptionDto findAllByPeriodId(Long periodId, int pageIndex, int pageSize) {
        ResumeConsumptionDto response = new ResumeConsumptionDto();
        Optional<PeriodEntity> period = periodRepository.findById(periodId);
        if (period.isPresent()) {
            response.setStatus(period.get().getStatus());
            response.setStartDate(period.get().getStartDate());
            response.setEndDate(period.get().getEndDate());

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page page = consumptionRepository.findAllByPeriod(period.get().getId(), pageable);
            response.setDetail(page.getContent());
            response.getDetail().parallelStream().forEach(item -> {
                Optional<ConsumptionEntity> consumption = consumptionRepository.findById(item.getConsumptionId());
                if (consumption.isPresent()) {
                    List<BillDetailEntity> billDetails = billDetailService.getDetail(consumption.get(), null);
                    item.setAmountToPaid(billDetails.stream().mapToLong(SalesDocumentDetailEntity::getTotalAmount).sum());
                }
            });
            response.setTotalHits(page.getTotalElements());
        }
        return response;
    }

    public ConsumptionClientDto findAllByClient(String rut, Integer pageIndex, Integer pageSize) {
        ClientEntity client = clientRepository.findByRut(rut).orElseThrow(() -> new ClientNotFoundException(rut));
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page page = consumptionRepository.findAllByClient(rut, pageable);
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
     * @param periodId
     */
    @Async
    public void createAllRecords(Long periodId) {
        periodRepository.findById(periodId).ifPresent(period -> {
            waterMeterRepository.findAll().parallelStream().forEach(waterMeter -> {
                ConsumptionEntity consumptionEntity = new ConsumptionEntity();
                consumptionEntity.setConsumption(0L);
                consumptionEntity.setPeriod(period);
                consumptionEntity.setWaterMeter(waterMeter);
                consumptionRepository.save(consumptionEntity);
            });
        });
    }
}
