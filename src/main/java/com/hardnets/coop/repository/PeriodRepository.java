package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity, Long> {

    @Query("select p from PeriodEntity p where p.status = ?1")
    List<PeriodEntity> findByStatus(PeriodStatusEnum status);

    List<PeriodEntity> findAllByStatusEquals(PeriodStatusEnum periodStatusEnum);

    @Query("select distinct year(p.startDate) from PeriodEntity p")
    Set<Integer> findAllYears();

    Optional<PeriodEntity> findFirstByIdNot(@NonNull Long id);
}
