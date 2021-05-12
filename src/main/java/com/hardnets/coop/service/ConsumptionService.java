package com.hardnets.coop.service;

import com.hardnets.coop.dto.ReadingsDto;
import com.hardnets.coop.dto.response.ConsumptionClientDto;
import com.hardnets.coop.dto.response.ResumeConsumptionDto;
import com.hardnets.coop.entity.ClientEntity;
import com.hardnets.coop.entity.ConsumptionEntity;
import com.hardnets.coop.entity.PeriodEntity;
import com.hardnets.coop.entity.WaterMeterEntity;
import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public List<ReadingsDto> findRecordsByWaterMeterId(Long id) {
        Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findById(id);
        if (waterMeter.isPresent()) {
            return consumptionRepository.findAllByWaterMeter(waterMeter.get().getId());
        }
        return new ArrayList<>();
    }

    public ConsumptionEntity create(Long id, Long consumption, PeriodEntity period) {
        WaterMeterEntity waterMeter = waterMeterRepository.findById(id).orElseThrow(() -> new WaterMeterNotFoundException("Water meter number " + id + " was " +
                "not" + " found"));
        ConsumptionEntity consumptionEntity = new ConsumptionEntity();
        consumptionEntity.setCreated(LocalDateTime.now());
        consumptionEntity.setConsumption(consumption);
        consumptionEntity.setReadingDate(new Date());
        consumptionEntity.setWaterMeter(waterMeter);
        consumptionEntity.setPeriod(period);
        return consumptionRepository.save(consumptionEntity);
    }

    public ResumeConsumptionDto findAllByPeriodId(Long periodId, int pageIndex, int pageSize) {
        ResumeConsumptionDto response = new ResumeConsumptionDto();
        Optional<PeriodEntity> period = periodRepository.findById(periodId);
        if (period.isPresent()) {
            response.setStatus(period.get().getStatus());
            response.setStartDate(period.get().getStartDate());
            response.setEndDate(period.get().getEndDate());

            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            Page page = consumptionRepository.findAllByPeriodId(period.get().getId(), pageable);
            response.setDetail(page.getContent());
            response.setTotalHits(page.getTotalElements());
        }
        return response;
    }

    public ConsumptionClientDto findAllByClient(String rut) {
        ClientEntity client = clientRepository.findByRut(rut).orElseThrow(() -> new ClientNotFoundException(rut));
        Pageable pageable = PageRequest.of(0, 25);
        Page page = consumptionRepository.findAllByClient(rut, pageable);
        ConsumptionClientDto pageDto = new ConsumptionClientDto();
        pageDto.setFullName(client.getFullName());
        pageDto.setTotalHits(1L);
        pageDto.setContent(page.getContent());
        return pageDto;
    }
}
