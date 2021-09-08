package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.dto.response.PeriodDto;
import com.hardnets.coop.model.entity.PeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity, Long> {

    @Query("select p from PeriodEntity p where p.status = ?1")
    Optional<PeriodEntity> findByStatus(PeriodStatusEnum status);

    @Query("select new com.hardnets.coop.model.dto.response.PeriodDto(p.id, p.startDate, p.endDate, p.status) from PeriodEntity p where YEAR(p.startDate) = :year")
    Set<PeriodDto> findAllDto(@Param("year") Integer year);

    @Query("select distinct year(p.startDate) from PeriodEntity p")
    Set<Integer> findAllYears();

    PeriodEntity findFirstByIdNot(Long id);
}
