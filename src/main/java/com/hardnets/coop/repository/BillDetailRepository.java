package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.BillDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Long> {
}
