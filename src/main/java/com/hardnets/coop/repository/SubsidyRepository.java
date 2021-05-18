package com.hardnets.coop.repository;

import com.hardnets.coop.entity.SubsidyEntity;
import com.hardnets.coop.entity.WaterMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubsidyRepository extends JpaRepository<SubsidyEntity, Long> {

    @Query("select s from SubsidyEntity s where s.waterMeter.id = ?1")
    Optional<SubsidyEntity> findByWaterMeterId(Long number);

    Optional<SubsidyEntity> findAllByWaterMeterAndIsActive(WaterMeterEntity waterMeter, Boolean isActive);

}
