package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.ReadingsDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends PagingAndSortingRepository<ConsumptionEntity, Long> {

    @Query("select new com.hardnets.coop.model.dto.ReadingsDto(c.id, c.reading,c.readingDate) from ConsumptionEntity c where c.waterMeter.id = ?1 order by c.readingDate desc")
    List<ReadingsDto> findAllByWaterMeter(Long waterMeterId);

    @Query(value = "select new com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto(wm.serial, c.waterMeter.client.rut, " +
            "c.waterMeter.client.clientType, c.waterMeter.client.names, c.waterMeter.client.middleName, c.waterMeter.client.lastName, " +
            "c.waterMeter.client.businessName, c.reading, c.id) from ConsumptionEntity c inner join c.waterMeter wm where c.period.id = ?1",
            countQuery = "select count(c) from ConsumptionEntity c where c.period.id = ?1"
    )
    Page<ResumeConsumptionDetailDto> findAllByPeriod(Long id, Pageable pageable);

    List<ConsumptionEntity> findAllByPeriod(PeriodEntity period);

    @Query(value = "select new com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto(c.readingDate, pe.endDate, c.reading) " +
            "from ConsumptionEntity c inner join c.waterMeter.client cl inner join c.period pe where cl.rut = ?1 and c.reading > 0 order by pe.id desc",
            countQuery = "select count(c) from ConsumptionEntity c inner join c.waterMeter.client cl where cl.rut = ?1 and c.reading > 0")
    Page<ConsumptionClientDetailDto> findAllByClient(String rut, Pageable pageable);

    ConsumptionEntity findAllByPeriodAndWaterMeter(PeriodEntity period, WaterMeterEntity waterMeter);
}
