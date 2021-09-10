package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.ConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumptionRepositoryCrud extends JpaRepository<ConsumptionEntity, Long> {
}
