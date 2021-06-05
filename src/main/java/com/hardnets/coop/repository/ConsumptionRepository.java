package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.ReadingsDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends PagingAndSortingRepository<ConsumptionEntity, Long> {

    @Query("select new com.hardnets.coop.model.dto.ReadingsDto(c.id, c.consumption,c.readingDate) from ConsumptionEntity c where c.waterMeter.id = ?1 order by c.readingDate desc")
    List<ReadingsDto> findAllByWaterMeter(Long waterMeterId);

    @Query(value = "select new com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto(ce.rut, ce.clientType.code, ce.names, ce.middleName, ce.lastName, ce.businessName, " +
            "c.consumption, c.id) from ConsumptionEntity c, ClientEntity ce where c.period.id = ?1",
            countQuery = "select count(c) from ConsumptionEntity c where c.period.id = ?1"
    )
    Page<ResumeConsumptionDetailDto> findAllByPeriodId(Long id, Pageable pageable);

    List<ConsumptionEntity> findAllByPeriod(PeriodEntity period);

    @Query(value = "select new com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto(c.readingDate, c.period.endDate, c.consumption) from ConsumptionEntity c, ClientEntity ce where ce.rut = ?1",
            countQuery = "select count(c) from ConsumptionEntity c, ClientEntity ce where ce.rut = ?1")
    Page<ConsumptionClientDetailDto> findAllByClient(String rut, Pageable pageable);
}
