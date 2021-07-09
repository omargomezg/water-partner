package com.hardnets.coop.service;

import com.hardnets.coop.exception.DropDownNotFoundException;
import com.hardnets.coop.exception.HandleException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.exception.WaterMeterException;
import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.WaterMetersConsumptionDto;
import com.hardnets.coop.model.dto.response.RelatedWaterMetersDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WaterMeterService {

    private final WaterMeterRepository waterMeterRepository;
    private final ClientRepository clientRepository;
    private final DropDownListRepository dropDownListRepository;
    private final SubsidyRepository subsidyRepository;
    private final TariffRepository tariffRepository;
    private final PeriodRepository periodRepository;

    public void update(List<WaterMeterDto> waterMeterDtos) {
        List<WaterMeterEntity> entities = new ArrayList<>();
        for (WaterMeterDto dto : waterMeterDtos) {
            WaterMeterEntity entity = waterMeterRepository.findByNumber(dto.getNumber()).orElseThrow(() ->
                    new WaterMeterNotFoundException("Water meter number " + dto.getNumber() + " was not found"));
            ClientEntity client = clientRepository.findByRut(dto.getRut())
                    .orElseThrow(() -> new UserNotFoundException("User by rut " + dto.getRut() + " was not" + " found"));
            entity.setClient(client);
            entities.add(entity);
        }
        waterMeterRepository.saveAll(entities);
    }

    public WaterMeterDto update(WaterMeterDto waterMeterDto) {
        WaterMeterEntity entity = waterMeterRepository.findByNumber(waterMeterDto.getNumber()).orElseThrow(() ->
                new WaterMeterNotFoundException("Water meter number " + waterMeterDto.getNumber() + " was not found"));
        DropDownListEntity size = dropDownListRepository.findById(waterMeterDto.getSizeId())
                .orElseThrow(() -> new DropDownNotFoundException("Size not found"));

        entity.setDescription(waterMeterDto.getComment());
        entity.setNumber(waterMeterDto.getNumber());
        entity.setSector(waterMeterDto.getSector());
        entity.setTrademark(waterMeterDto.getTrademark());
        entity.setSize(size);
        Optional<ClientEntity> client = clientRepository.findByRut(waterMeterDto.getRut());
        client.ifPresent(entity::setClient);
        waterMeterRepository.save(entity);
        WaterMeterDto dto = new WaterMeterDto(entity);
        dto.setRut(entity.getClient().getRut());
        return dto;
    }

    public WaterMeterDto create(WaterMeterDto waterMeterDto) {
        DropDownListEntity size = dropDownListRepository.findById(waterMeterDto.getSizeId())
                .orElseThrow(() -> new DropDownNotFoundException("Size not found"));
        Optional<WaterMeterEntity> dbWaterMeter = waterMeterRepository.findFirstByNumberEqualsAndSizeAndTrademark(waterMeterDto.getNumber(), size, waterMeterDto.getTrademark());
        if (dbWaterMeter.isPresent()) {
            throw new WaterMeterException("Other water meter exists with same number, trademark and size");
        }
        DropDownListEntity status = dropDownListRepository.findByCode("NEW")
                .orElseThrow(() -> new DropDownNotFoundException("Status not found"));
        WaterMeterEntity waterMeter = new WaterMeterEntity();
        waterMeter.setDescription(waterMeterDto.getComment());
        waterMeter.setNumber(waterMeterDto.getNumber());
        waterMeter.setTrademark(waterMeterDto.getTrademark());
        waterMeter.setSector(waterMeterDto.getSector());
        waterMeter.setSize(size);
        waterMeter.setCreated(new Date());
        waterMeter.setStatus(status);
        return new WaterMeterDto(
                waterMeterRepository.save(waterMeter)
        );
    }

    public WaterMeterDto getByNumber(String number) {
        WaterMeterEntity waterMeter = waterMeterRepository.findByNumber(number)
                .orElseThrow(() -> new WaterMeterNotFoundException("Water meter number " + number + " was " +
                        "not" + " found"));
        return new WaterMeterDto(waterMeter);
    }

    public WaterMeterDto getById(Long id) {
        WaterMeterEntity waterMeter = waterMeterRepository.findById(id)
                .orElseThrow(() -> new WaterMeterNotFoundException("Water meter number " + id + " was " +
                        "not" + " found"));
        return new WaterMeterDto(waterMeter);
    }

    public Collection<WaterMeterDto> findAllWheregetNotRelated() {
        return waterMeterRepository.findAllWhereClientIsNull();
    }

    public Collection<WaterMeterDto> getAll() {
        Collection<WaterMeterEntity> waterMeterEntities = waterMeterRepository.findAll();
        return waterMeterEntities.stream().map(WaterMeterDto::new)
                .sorted(Comparator.comparing(WaterMeterDto::getUpdated).reversed())
                .collect(Collectors.toList());
    }

    public Collection<RelatedWaterMetersDto> getByUser(String rut) {
        Collection<RelatedWaterMetersDto> relatedMeters = new HashSet<>();
        ClientEntity client = clientRepository.findByRut(rut)
                .orElseThrow(() -> new UserNotFoundException("User by rut " + rut + " was not" + " found"));
        Collection<WaterMeterEntity> dbRelatedMeters = waterMeterRepository.findAllByClientOrderByUpdatedDesc(client);
        for (WaterMeterEntity dbRelatedMeter : dbRelatedMeters) {
            Optional<SubsidyEntity> subsidy =
                    subsidyRepository.findAllByWaterMeterAndIsActiveAndEndingDateAfter(dbRelatedMeter, true,
                            new Date());
            Optional<TariffEntity> tariff = tariffRepository.findBySizeAndClientType(dbRelatedMeter.getSize().getId(), client.getClientType().getId());
            RelatedWaterMetersDto related = new RelatedWaterMetersDto(
                    dbRelatedMeter.getId(),
                    dbRelatedMeter.getNumber(),
                    dbRelatedMeter.getSize().getValue(),
                    dbRelatedMeter.getCreated(),
                    dbRelatedMeter.getSector(),
                    tariff.isPresent() ? tariff.get().getFlatFee() : 0,
                    tariff.isPresent() ? tariff.get().getCubicMeter() : 0,
                    subsidy.isPresent() ? subsidy.get().getPercentage() : 0
            );
            relatedMeters.add(related);
        }
        return relatedMeters.stream().sorted(Comparator.comparing(RelatedWaterMetersDto::getDischargeDate)).collect(Collectors.toList());
    }

    public boolean relateToClient(WaterMeterDto waterMeterDto, String rut) {
        String waterMeterNumber = waterMeterDto.getNumber();
        ClientEntity client = clientRepository.findByRut(rut)
                .orElseThrow(() -> new UserNotFoundException("User by rut " + rut + " was not" + " found"));
        waterMeterRepository.findByNumber(waterMeterDto.getNumber()).ifPresent(result -> {
            if (result.getClient() != null) {
                throw new HandleException("Water meter already related to user " + result.getClient().getFullName());
            }
        });
        DropDownListEntity status = dropDownListRepository.findByCode("NEW")
                .orElseThrow(() -> new DropDownNotFoundException("Status not found"));
        DropDownListEntity size = dropDownListRepository.findById(waterMeterDto.getSizeId())
                .orElseThrow(() -> new DropDownNotFoundException("Size not found"));
        WaterMeterEntity wEntity = new WaterMeterEntity();
        wEntity.setNumber(waterMeterNumber);
        wEntity.setTrademark(waterMeterDto.getTrademark());
        wEntity.setCreated(new Date());
        wEntity.setSector(waterMeterDto.getSector());
        wEntity.setStatus(status);
        wEntity.setSize(size);
        wEntity.setDescription(waterMeterDto.getComment());
        wEntity.setClient(client);
        waterMeterRepository.save(wEntity);
        return true;
    }

    public Collection<WaterMetersConsumptionDto> findAllForSetConsumption(String number, String rut, boolean pendingConsumption) {
        Optional<PeriodEntity> periodEntity = periodRepository.findByStatus("ACTIVE");
        if (periodEntity.isPresent())
            return waterMeterRepository.findAllByCustomFilters(number, rut, periodEntity.get().getId(), pendingConsumption);
        return new ArrayList<>();
    }

}
