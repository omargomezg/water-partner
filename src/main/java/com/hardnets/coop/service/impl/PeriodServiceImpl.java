package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.PeriodException;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.PeriodFilterRequest;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.PeriodService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;
    private final ModelMapper modelMapper;
    private final ConversionService conversionService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PeriodEntity> findAll(PeriodFilterRequest filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PeriodEntity> cq = cb.createQuery(PeriodEntity.class);
        Root<PeriodEntity> root = cq.from(PeriodEntity.class);
        var predicates = buildPredicates(filter, cb, root);
        if (predicates.isEmpty()) {
            cq.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        if (filter.getSortBy() != null) {
            if (filter.getDescending()) {
                cq.orderBy(cb.desc(root.get(filter.getSortBy())));
            } else {
                cq.orderBy(cb.asc(root.get(filter.getSortBy())));
            }
        }
        TypedQuery<PeriodEntity> result = em.createQuery(cq);
        int page = filter.getPage() != null ? filter.getPage() : 0;
        int size = filter.getSize() != null ? filter.getSize() : 10;
        int startPosition = page * size;
        result.setFirstResult(startPosition);
        result.setMaxResults(size);
        return result.getResultList();
    }

    @Override
    public Long totalElements(PeriodFilterRequest filter) {
        return 1L;
    }

    @Override
    public List<PeriodEntity> findAllByYear(int year) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PeriodEntity> cq = cb.createQuery(PeriodEntity.class);
        Root<PeriodEntity> root = cq.from(PeriodEntity.class);
        cq.where(cb.equal(cb.function("year", Integer.class, root.get("startDate")), year));
        TypedQuery<PeriodEntity> result = em.createQuery(cq);
        return result.getResultList();
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
    public Optional<PeriodEntity> findByStatus(PeriodStatusEnum status) {
        return periodRepository.findByStatus(status).stream().findFirst();
    }

    @Transactional
    @Override
    public PeriodEntity close(Long id) {
        PeriodEntity period = periodRepository.findById(id).orElseThrow(() -> new PeriodException("Period was not found"));
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

    @Transactional
    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        var criteriaDelete = cb.createCriteriaDelete(PeriodEntity.class);
        var root = criteriaDelete.from(PeriodEntity.class);
        criteriaDelete.where(cb.equal(root.get("id"), id));
        em.createQuery(criteriaDelete).executeUpdate();
    }

    private List<Predicate> buildPredicates(PeriodFilterRequest filter, CriteriaBuilder cb, Root<PeriodEntity> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), filter.getStatus()));
        }
        return predicates;
    }

    @Override
    public PeriodEntity initPeriod(Long id) {
        PeriodEntity period = periodRepository.findById(id).orElseThrow(() -> new PeriodException("Period was not found"));
        period.setStatus(PeriodStatusEnum.ACTIVE);
        return periodRepository.save(period);
    }

}
