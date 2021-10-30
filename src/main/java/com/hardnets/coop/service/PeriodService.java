package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface PeriodService {

    Set<PeriodDto> findAllByYear(int year);

    Set<Integer> findAllYears();

    Optional<PeriodEntity> findById(long id);

    PeriodDto update(PeriodDto periodEntity);

    PeriodDto create(PeriodDto periodEntity);

    PeriodEntity findByStatus(PeriodStatusEnum status);

    /**
     * Cierra un periodo y abre uno nuevo
     *
     * @param id Identificador de periodo
     */
    PeriodEntity close(Long id);

    PeriodEntity create(Date startDate);

}
