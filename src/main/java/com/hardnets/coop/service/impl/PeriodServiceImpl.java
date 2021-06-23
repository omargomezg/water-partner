package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.PeriodService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PeriodServiceImpl implements PeriodService {

    public static final String ACTIVE = "ACTIVE";
    private final PeriodRepository periodRepository;


    @Override
    public List<PeriodEntity> findAll() {
        return periodRepository.findAll().stream().sorted(Comparator.comparing(PeriodEntity::getId).reversed())
                .collect(Collectors.toList());
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
        if (status.equals(ACTIVE)) {
            PeriodEntity newPeriod = new PeriodEntity();
            newPeriod.setStatus(ACTIVE);
            newPeriod.setStartDate(new Date());
            return periodRepository.save(newPeriod);
        }
        throw new PeriodException("Cannot find period with status " + status);
    }

    @Override
    public PeriodEntity close(Long id) {
        PeriodEntity period = periodRepository.findById(id).orElseThrow(
                () -> new PeriodException("Period was not found")
        );
        Date endDate = new Date();
        period.setEndDate(endDate);
        period.setStatus("CLOSED");
        periodRepository.save(period);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DATE, 1);
        return create(calendar.getTime());
        //TODO crear boletas o detalle de boleas (servicio asíncrono)
        //TODO crear pdf o boletas
        //TODO enviar boletas a clientes
    }

    @Override
    public PeriodEntity create(Date startDate) {
        PeriodEntity newPeriod = new PeriodEntity();
        newPeriod.setStartDate(startDate);
        newPeriod.setStatus(ACTIVE);
        return periodRepository.save(newPeriod);
    }
}
