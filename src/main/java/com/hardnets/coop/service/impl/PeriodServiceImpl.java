package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;
    private final ModelMapper modelMapper;
    private final ConversionService conversionService;


    @Override
    public Set<PeriodDto> findAll(Optional<PeriodStatusEnum> periodStatus) {
        var periods = periodStatus.isPresent() ?
                periodRepository.findAllByStatusEquals(periodStatus.get()) :
                periodRepository.findAll();
        return periods.stream().map(period -> modelMapper.map(period, PeriodDto.class))
                .sorted(Comparator.comparing(PeriodDto::getStartDate))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<PeriodDto> findAllByYear(int year) {
        return periodRepository.findAllDto(year);
    }

    @Override
    public Set<Integer> findAllYears() {
        return periodRepository.findAllYears();
    }

    @Override
    public Optional<PeriodEntity> findById(long id) {
        return periodRepository.findById(id);
    }

    @Override
    public PeriodDto update(PeriodDto periodDto) {
        PeriodEntity dbPeriod = periodRepository.findById(periodDto.getId()).orElseThrow(PeriodException::new);
        var periodEntity = conversionService.convert(periodDto, PeriodEntity.class);
        dbPeriod.setStatus(periodEntity.getStatus());
        dbPeriod.setEndDate(periodEntity.getEndDate());
        periodRepository.save(dbPeriod);
        return modelMapper.map(dbPeriod, PeriodDto.class);
    }

    @Override
    public PeriodDto create(PeriodDto periodDto) {
        var periodEntity = modelMapper.map(periodDto, PeriodEntity.class);
        periodEntity.setStatus(PeriodStatusEnum.PREPARED);
        periodEntity = periodRepository.save(periodEntity);
        return modelMapper.map(periodEntity, PeriodDto.class);
    }

    @Override
    public PeriodEntity findByStatus(PeriodStatusEnum status) {
        List<PeriodEntity> period = periodRepository.findByStatus(status);
        if (period.isEmpty() && status.equals(PeriodStatusEnum.ACTIVE)) {
            PeriodEntity newPeriod = new PeriodEntity();
            newPeriod.setStatus(status);
            newPeriod.setStartDate(new Date());
            return periodRepository.save(newPeriod);
        }
        return period.stream().findFirst().get();
    }

    @Transactional
    @Override
    public PeriodEntity close(Long id) {
        PeriodEntity period = periodRepository.findById(id).orElseThrow(
                () -> new PeriodException("Period was not found")
        );
        Date endDate = new Date();
        period.setEndDate(endDate);
        period.setStatus(PeriodStatusEnum.CLOSED);
        return enableNewPeriod(periodRepository.save(period));
    }

    private PeriodEntity enableNewPeriod(PeriodEntity actualPeriod) {
        Optional<PeriodEntity> newPeriod = periodRepository.findById(actualPeriod.getId() + 1);
        if (newPeriod.isPresent()) {
            return newPeriod.get();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(actualPeriod.getEndDate());
            calendar.add(Calendar.DATE, 1);
            return create(calendar.getTime());
        }
    }

    @Override
    public PeriodEntity create(Date startDate) {
        PeriodEntity newPeriod = new PeriodEntity();
        newPeriod.setStartDate(startDate);
        newPeriod.setStatus(PeriodStatusEnum.ACTIVE);
        return periodRepository.save(newPeriod);
    }
}
