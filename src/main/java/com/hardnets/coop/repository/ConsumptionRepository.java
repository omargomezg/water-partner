package com.hardnets.coop.repository;

import com.hardnets.coop.dto.ReadingsDto;
import com.hardnets.coop.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.entity.ConsumptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends JpaRepository<ConsumptionEntity, Long> {

    @Query("select new com.hardnets.coop.dto.ReadingsDto(c.id, c.consumption,c.readingDate) from ConsumptionEntity c where c.waterMeter.id = ?1 order by c.readingDate desc")
    List<ReadingsDto> findAllByWaterMeter(Long waterMeterId);

    @Query("select new com.hardnets.coop.dto.response.ResumeConsumptionDetailDto(ce.rut, ce.clientType.code, ce.names, ce.middleName, ce.lastName, ce.businessName, " +
            "c.consumption) from ConsumptionEntity c, ClientEntity ce where c.period.id = ?1")
    List<ResumeConsumptionDetailDto> findAllByPeriodId(Long id);
}
