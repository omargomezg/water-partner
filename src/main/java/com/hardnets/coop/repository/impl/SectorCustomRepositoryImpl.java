package com.hardnets.coop.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hardnets.coop.model.dto.SectorFilterRequest;
import com.hardnets.coop.model.entity.SectorEntity;
import com.hardnets.coop.repository.SectorCustomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class SectorCustomRepositoryImpl implements SectorCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<SectorEntity> findByFilter(SectorFilterRequest filter) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(SectorEntity.class);
        var root = query.from(SectorEntity.class);
        var predicates = buildPredicates(filter, cb, root);
        query.where(predicates.toArray(new Predicate[0]));
        return Optional.ofNullable(em.createQuery(query).getSingleResult());
    }

    @Override
    public Optional<SectorEntity> findById(Long id) {
        var filter = SectorFilterRequest.builder().id(id).build();
        return findByFilter(filter);
    }

    private  List<Predicate> buildPredicates(SectorFilterRequest filter, CriteriaBuilder cb, Root<SectorEntity> root) {
        var predicates = new ArrayList<Predicate>();
        if (filter.getId() != null) {
            predicates.add(cb.equal(root.get("id"), filter.getId()));
        }
        if (filter.getName() != null) {
            predicates.add(cb.equal(root.get("name"), filter.getName()));
        }
        return predicates;
    }

    
}
