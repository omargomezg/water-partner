package com.hardnets.coop.service.impl;

import com.hardnets.coop.dto.response.PeriodDto;
import com.hardnets.coop.entity.PeriodEntity;
import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;

    @Autowired
    public PeriodServiceImpl(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
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
    public PeriodEntity findById(long id) {
        return null;
    }

    @Override
    public PeriodEntity save(PeriodEntity periodEntity) {
        return null;
    }

    @Override
    public PeriodEntity findByStatus(String status) {
        Optional<PeriodEntity> period = periodRepository.findByStatus(status);
        if (period.isPresent()) {
            return period.get();
        }
        if (status.equals("ACTIVE")) {
            PeriodEntity newPeriod = new PeriodEntity();
            newPeriod.setStatus("ACTIVE");
            newPeriod.setStartDate(new Date());
            return periodRepository.save(newPeriod);
        }
        throw new PeriodException("Cannot find period with status " + status);
    }
}
