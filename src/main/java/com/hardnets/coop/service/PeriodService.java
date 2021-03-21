package com.hardnets.coop.service;

import com.hardnets.coop.dto.response.PeriodDto;
import com.hardnets.coop.entity.PeriodEntity;

import java.util.Set;

public interface PeriodService {

    Set<PeriodDto> findAllByYear(int year);

    Set<Integer> findAllYears();

    PeriodEntity findById(long id);

    PeriodEntity save(PeriodEntity periodEntity);

    PeriodEntity findByStatus(String status);

}
