package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;

import java.util.Date;
import java.util.Set;

public interface PeriodService {

    Set<PeriodDto> findAllByYear(int year);

    Set<Integer> findAllYears();

    PeriodEntity findById(long id);

    PeriodEntity save(PeriodEntity periodEntity);

    PeriodEntity findByStatus(String status);

    void closePeriod(Long id);

    void create(Date startDate);

}
