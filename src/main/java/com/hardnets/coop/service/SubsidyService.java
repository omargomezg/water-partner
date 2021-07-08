package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.request.UserSubsidyRequest;
import com.hardnets.coop.model.dto.response.SubsidyDto;
import com.hardnets.coop.model.entity.DecreeEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.DecreeRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubsidyService {

    private final SubsidyRepository subsidyRepository;
    private final WaterMeterRepository waterMeterRepository;
    private final DecreeRepository decreeRepository;

    /**
     * Obtiene un subsidio activo por medidor
     *
     * @param waterMeterId Identificador del medidor
     * @return Un subsidio
     */
    public SubsidyDto findByWaterMeterId(Long waterMeterId) {
        SubsidyEntity subsidy = subsidyRepository.findByIsActiveAndWaterMeterId(waterMeterId)
                .orElse(new SubsidyEntity());
        SubsidyDto dto = parseToDto(subsidy);
        if (Objects.nonNull(subsidy.getWaterMeter())) {
            var waterMeter = new WaterMeterDto();
            waterMeter.setNumber(subsidy.getWaterMeter().getNumber());
            waterMeter.setId(subsidy.getWaterMeter().getId());
            dto.setWaterMeter(waterMeter);
        }
        if (Objects.nonNull(subsidy.getDecree())) {
            dto.setNumberOfDecree(subsidy.getDecree().getNumber());
            dto.setApprovedDateOfDecree(subsidy.getDecree().getApproved());
        }
        return dto;
    }

    public SubsidyEntity update(UserSubsidyRequest request) {
        var subsidyEntity = subsidyRepository.findById(request.getSubsidy().getId())
                .orElse(new SubsidyEntity());
        Optional<WaterMeterEntity> waterMeter = waterMeterRepository.findById(request.getWaterMeter().getId());
        waterMeter.ifPresent(subsidyEntity::setWaterMeter);
        DecreeEntity decree = decreeRepository.findFirstByNumberEquals(request.getDecree().getNumber())
                .orElse(new DecreeEntity());
        decree.setNumber(request.getDecree().getNumber());
        decree.setApproved(request.getDecree().getPublication());
        subsidyEntity.setDecree(decreeRepository.save(decree));
        subsidyEntity.setStartDate(request.getSubsidy().getStart());
        subsidyEntity.setEndingDate(request.getSubsidy().getEnd());
        subsidyEntity.setPercentage(request.getSubsidy().getPercentage());
        subsidyEntity.setObservation(request.getObservation());
        subsidyEntity.setUpdated(new Date());
        subsidyEntity.setIsActive(true);
        return subsidyRepository.save(subsidyEntity);
    }

    private SubsidyDto parseToDto(SubsidyEntity subsidy) {
        return new SubsidyDto(subsidy.getId(), subsidy.getStartDate(), subsidy.getEndingDate(),
                subsidy.getPercentage(), subsidy.getObservation());
    }
}
