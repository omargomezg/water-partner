package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface PeriodService {

    List<PeriodEntity> findAll();

    Set<PeriodDto> findAllByYear(int year);

    Set<Integer> findAllYears();

    PeriodEntity findById(long id);

    PeriodEntity save(PeriodEntity periodEntity);

    PeriodEntity findByStatus(String status);

    /**
     * Cierra un periodo y abre uno nuevo
     *
     * @param id Identificador de periodo
     */
    PeriodEntity close(Long id);

    PeriodEntity create(Date startDate);

}
