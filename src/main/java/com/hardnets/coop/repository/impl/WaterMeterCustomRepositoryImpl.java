package com.hardnets.coop.repository.impl;

import com.hardnets.coop.model.dto.WaterMeterFilterRequest;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.WaterMeterCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WaterMeterCustomRepositoryImpl implements WaterMeterCustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<WaterMeterEntity> getAllByPage(WaterMeterFilterRequest filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<WaterMeterEntity> cq = cb.createQuery(WaterMeterEntity.class);
		var root = cq.from(WaterMeterEntity.class);
		var predicates = buildPredicates(filter, cb, root);
		if (!predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[0]));
		}
		cq.orderBy(cb.desc(root.get("updatedAt")));
		var query = em.createQuery(cq);
		query.setFirstResult(filter.getPage() * filter.getSize());
		query.setMaxResults(filter.getSize());
		return query.getResultList();
	}

	@Override
	public Long getTotalOfElements(WaterMeterFilterRequest filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<WaterMeterEntity> root = cq.from(WaterMeterEntity.class);
		List<Predicate> predicates = buildPredicates(filter, cb, root);
		if (!predicates.isEmpty()) {
			cq.where(predicates.toArray(new Predicate[0]));
		}
		cq.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));
		return em.createQuery(cq).getSingleResult();
	}

	private List<Predicate> buildPredicates(WaterMeterFilterRequest filter, CriteriaBuilder cb,
			Root<WaterMeterEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (filter.getSerial() != null) {
			predicates.add(cb.equal(root.get("serial"), filter.getSerial()));
		}
		if (filter.getIsAssigned() != null) {
			if (filter.getIsAssigned()) {
				predicates.add(cb.isNotNull(root.get("client")));
			} else {
				predicates.add(cb.isNull(root.get("client")));
			}
		}
		if (filter.getText() != null && !filter.getText().isEmpty()) {
			String pattern = "%" + filter.getText().toLowerCase() + "%";
			Predicate serialPredicate = cb.equal(root.get("serial"), filter.getText());
			Predicate trademarkPredicate = cb.like(cb.lower(root.get("trademark")), pattern);
			predicates.add(cb.or(serialPredicate, trademarkPredicate));
		}
		return predicates;
	}

}
