package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetailEntity, Long> {
    List<BillDetailEntity> findAllByBill(BillEntity bill);
}
