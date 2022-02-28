package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.SubsidyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface SubsidyRepository extends JpaRepository<SubsidyEntity, Long> {

    @Query("select s from SubsidyEntity s where s.waterMeter.id = :id and s.isActive = true and s.endingDate >= " +
            ":endDate")
    Optional<SubsidyEntity> findByIsActiveAndWaterMeterId(@Param("id") Long number, @Param("endDate") Date endDate);

    @Query(value = "select sub from SubsidyEntity sub where sub.waterMeter.id = :meter_id and sub.isActive = :isActive and sub.endingDate > :endingDate")
    Optional<SubsidyEntity> findAllByWaterMeterAndIsActiveAndEndingDateAfter(@Param("meter_id") Long meter_id,
                                                                             @Param("isActive") Boolean isActive,
                                                                             @Param("endingDate") Date date);

}
