package com.hardnets.coop.service;

import com.hardnets.coop.dto.WaterMeterDto;
import com.hardnets.coop.dto.response.SubsidyDto;
import com.hardnets.coop.entity.SubsidyEntity;
import com.hardnets.coop.entity.WaterMeterEntity;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    private SubsidyDto parseToDto(SubsidyEntity subsidy) {
        return new SubsidyDto(subsidy.getId(), subsidy.getStartDate(), subsidy.getEndingDate(),
                subsidy.getPercentage(), subsidy.getObservation());
    }
}
