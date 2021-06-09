package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.response.SubsidyDto;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class SubsidyService {

    private final SubsidyRepository subsidyRepository;
    private final WaterMeterRepository waterMeterRepository;

    public SubsidyDto findByWaterMeterId(Long waterMeterId) {
        SubsidyEntity subsidy = subsidyRepository.findByIsActiveAndWaterMeterId(waterMeterId).orElse(new SubsidyEntity());
        SubsidyDto dto = parseToDto(subsidy);
        if (subsidy.getWaterMeter() != null) {
            WaterMeterDto waterMeter = new WaterMeterDto();
            waterMeter.setNumber(subsidy.getWaterMeter().getNumber());
            waterMeter.setId(subsidy.getWaterMeter().getId());
            dto.setWaterMeter(waterMeter);
        }
        return dto;
    }

    public void update(SubsidyDto subsidy) {
        SubsidyEntity subsidyEntity = new SubsidyEntity();
        if (subsidy.getId() != null) {
            subsidyEntity = subsidyRepository.findById(subsidy.getId()).orElse(new SubsidyEntity());
        }
        if (subsidy.getWaterMeter() != null) {
            Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findById(subsidy.getWaterMeter().getId());
            waterMeter.ifPresent(subsidyEntity::setWaterMeter);
        }
        subsidyEntity.setStartDate(subsidy.getStartDate());
        subsidyEntity.setEndingDate(subsidy.getEndingDate());
        subsidyEntity.setPercentage(subsidy.getPercentage());
        subsidyEntity.setObservation(subsidy.getObservation());
        subsidyEntity.setUpdated(new Date());
        subsidyEntity.setIsActive(true);
        subsidyRepository.save(subsidyEntity);
    }

    /**
     * Comprueba que el subsidio es valido para la fecha especificada como ultimo dia de cobertura
     *
     * @param id Id del subsidio
     */
    public boolean checkValidity(Long id) {
        AtomicBoolean status = new AtomicBoolean(true);
        subsidyRepository.findById(id).ifPresent(subsidy -> {
            if (subsidy.getEndingDate().compareTo(new Date()) > 0) {
                subsidy.setIsActive(false);
                status.set(false);
                subsidyRepository.save(subsidy);
            }
        });
        return status.get();
    }

    private SubsidyDto parseToDto(SubsidyEntity subsidy) {
        return new SubsidyDto(subsidy.getId(), subsidy.getStartDate(), subsidy.getEndingDate(),
                subsidy.getPercentage(), subsidy.getObservation());
    }
}
