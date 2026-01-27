package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.PeriodFilterRequest;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PeriodService {

    List<PeriodEntity> findAll(PeriodFilterRequest filter);

    Long totalElements(PeriodFilterRequest filter);

    List<PeriodEntity> findAllByYear(int year);

    Set<Integer> findAllYears();

    Optional<PeriodEntity> findById(long id);

    PeriodDto update(PeriodDto periodEntity);

    PeriodEntity initPeriod(Long id);

    PeriodDto create(PeriodDto periodEntity);

    Optional<PeriodEntity> findByStatus(PeriodStatusEnum status);

    /**
     * Cierra un periodo y abre uno nuevo
     *
     * @param id Identificador de periodo
     */
    PeriodEntity close(Long id);

    PeriodEntity create(Date startDate);

    void delete(Long id);

}
