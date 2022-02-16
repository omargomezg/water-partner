package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ConflictException;
import com.hardnets.coop.exception.HandleException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.ListOfWaterMeterDto;
import com.hardnets.coop.model.dto.MetersAvailableDto;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.pageable.record.RecordDto;
import com.hardnets.coop.model.dto.pageable.record.RecordsDto;
import com.hardnets.coop.model.dto.response.RelatedWaterMetersDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterPageableRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
@Log4j2
@Service
public class WaterMeterService {

    private final WaterMeterRepository waterMeterRepository;
    private final WaterMeterPageableRepository waterMeterPageableRepository;
    private final ClientRepository clientRepository;
    private final SubsidyRepository subsidyRepository;
    private final TariffRepository tariffRepository;
    private final PeriodRepository periodRepository;
    private final ModelMapper modelMapper;

    public void update(List<WaterMeterDto> waterMeterDtos) {
        List<WaterMeterEntity> entities = new ArrayList<>();
        for (WaterMeterDto dto : waterMeterDtos) {
            WaterMeterEntity entity = waterMeterRepository.findBySerial(dto.getSerial()).orElseThrow(() ->
                    new WaterMeterNotFoundException("Water meter number " + dto.getSerial() + " was not found"));
            ClientEntity client = clientRepository.findByRut(dto.getRut())
                    .orElseThrow(UserNotFoundException::new);
            entity.setClient(client);
            entities.add(entity);
        }
        waterMeterRepository.saveAll(entities);
    }

    public WaterMeterDto update(WaterMeterDto waterMeterDto) {
        var waterMeter = waterMeterRepository.findById(waterMeterDto.getId()).orElseThrow(WaterMeterNotFoundException::new);
        waterMeter.setDiameter(waterMeterDto.getDiameter());
        waterMeter.setTrademark(waterMeterDto.getTrademark());
        waterMeter.setSector(waterMeterDto.getSector());
        waterMeter.setUpdated(new Date());
        waterMeter.setDescription(waterMeterDto.getComment());
        return modelMapper.map(waterMeterRepository.save(waterMeter), WaterMeterDto.class);
    }

    public WaterMeterEntity create(WaterMeterEntity waterMeter) {
        return waterMeterRepository.saveAndFlush(waterMeter);
    }


    public WaterMeterDto create(WaterMeterDto waterMeterDto) {
        if (checkIfExistsSerial(waterMeterDto.getSerial())) {
            throw new ConflictException("El numero de serie ya existe");
        }
        WaterMeterEntity waterMeter = new WaterMeterEntity();
        waterMeter.setDescription(waterMeterDto.getComment());
        waterMeter.setSerial(waterMeterDto.getSerial());
        waterMeter.setTrademark(waterMeterDto.getTrademark());
        waterMeter.setSector(waterMeterDto.getSector());
        waterMeter.setDiameter(waterMeterDto.getDiameter());
        waterMeter.setCreated(new Date());
        waterMeter.setStatus(StatusEnum.NEW);
        return new WaterMeterDto(
                waterMeterRepository.save(waterMeter)
        );
    }

    public boolean existSerial(Integer serial) {
        return waterMeterRepository.findBySerial(serial).isPresent();
    }

    public WaterMeterEntity getBySerial(Integer serial) {
        return waterMeterRepository.findBySerial(serial)
                .orElseThrow(WaterMeterNotFoundException::new);
    }

    public WaterMeterEntity getById(Long id) {
        return waterMeterRepository.findById(id)
                .orElseThrow(WaterMeterNotFoundException::new);
    }

    public MetersAvailableDto findAllWhereNotRelated(Integer pageIndex, Integer pageSize) {
        MetersAvailableDto meters = new MetersAvailableDto();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        var result = waterMeterPageableRepository.findAllWhereClientIsNull(pageable);

        meters.getMeters().addAll(result.getContent().stream().map(meter -> modelMapper.map(meter, WaterMeterDto.class))
                .collect(Collectors.toList()));
        meters.setTotalHits(result.getTotalElements());
        return meters;
    }

    public ListOfWaterMeterDto getAllByPage(Integer pageIndex, Integer pageSize, Optional<Integer> serial) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("updated").descending());
        Page<WaterMeterEntity> page = serial.isPresent() ? waterMeterPageableRepository.findBySerial(serial.get(), pageable) :
                waterMeterPageableRepository.findAll(pageable);
        ListOfWaterMeterDto result = new ListOfWaterMeterDto();
        result.setTotalHits(page.getTotalElements());
        page.getContent().forEach(meter ->
                result.getContents().add(modelMapper.map(meter, WaterMeterDto.class))
        );
        return result;
    }

    public List<WaterMeterDto> getAll() {
        List<WaterMeterEntity> waterMeterEntities = waterMeterRepository.findAll();
        return waterMeterEntities.stream().map(WaterMeterDto::new)
                .sorted(Comparator.comparing(WaterMeterDto::getUpdated).reversed())
                .collect(Collectors.toList());
    }

    public Collection<RelatedWaterMetersDto> getByUser(String rut) {
        Collection<RelatedWaterMetersDto> relatedMeters = new HashSet<>();
        ClientEntity client = clientRepository.findByRut(rut)
                .orElseThrow(UserNotFoundException::new);
        Collection<WaterMeterEntity> dbRelatedMeters = waterMeterRepository.findAllByClientOrderByUpdatedDesc(client);
        for (WaterMeterEntity dbRelatedMeter : dbRelatedMeters) {
            Optional<SubsidyEntity> subsidy =
                    subsidyRepository.findAllByWaterMeterAndIsActiveAndEndingDateAfter(dbRelatedMeter.getId(), true,
                            new Date());
            Optional<TariffEntity> tariff = tariffRepository.findBySizeAndClientType(dbRelatedMeter.getDiameter(),
                    client.getClientType());
            RelatedWaterMetersDto related = new RelatedWaterMetersDto(
                    dbRelatedMeter.getId(),
                    dbRelatedMeter.getSerial(),
                    dbRelatedMeter.getDiameter(),
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

    public void relateToClient(WaterMeterDto waterMeterDto, String rut) {
        Integer serial = waterMeterDto.getSerial();
        ClientEntity client = clientRepository.findByRut(rut)
                .orElseThrow(UserNotFoundException::new);
        waterMeterRepository.findBySerial(waterMeterDto.getSerial()).ifPresent(result -> {
            if (result.getClient() != null) {
                throw new HandleException("Water meter already related to user " + result.getClient().getFullName());
            }
        });
        WaterMeterEntity wEntity = new WaterMeterEntity();
        wEntity.setSerial(serial);
        wEntity.setTrademark(waterMeterDto.getTrademark());
        wEntity.setCreated(new Date());
        wEntity.setSector(waterMeterDto.getSector());
        wEntity.setStatus(StatusEnum.NEW);
        wEntity.setDiameter(waterMeterDto.getDiameter());
        wEntity.setDescription(waterMeterDto.getComment());
        wEntity.setClient(client);
        waterMeterRepository.save(wEntity);
    }

    public RecordsDto findAllForSetConsumption(String number,
                                               String rut,
                                               String sector,
                                               String status,
                                               Integer pageIndex,
                                               Integer pageSize) {
        RecordsDto recordsDto = new RecordsDto();
        List<PeriodEntity> periodEntity = periodRepository.findByStatus(PeriodStatusEnum.ACTIVE);
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        var consumptions = waterMeterPageableRepository.findAllByCustomFilters(
                number == null ? null : Integer.parseInt(number),
                rut,
                sector,
                periodEntity.stream().findFirst().get().getId(),
                pageable
        );
        recordsDto.setRecords(getFilteredByStatus(status, consumptions.getContent()));
        recordsDto.setTotalHits(consumptions.getTotalElements());
        return recordsDto;
    }

    private boolean checkIfExistsSerial(Integer serial) {
        return waterMeterRepository.findBySerial(serial).isPresent();
    }

    private List<RecordDto> getFilteredByStatus(String status, List<RecordDto> readings) {
        switch (status.toLowerCase()) {
            case "pending":
                return readings.stream().filter(item -> item.getReading() == 0)
                        .collect(Collectors.toList());
            case "no-pending":
                return readings.stream().filter(item -> item.getReading() > 0)
                        .collect(Collectors.toList());
            default:
                return readings;
        }
    }

}
