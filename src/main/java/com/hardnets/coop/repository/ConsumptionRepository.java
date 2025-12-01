package com.hardnets.coop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hardnets.coop.model.dto.ReadingsDto;
import com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto;
import com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;

@Repository
public interface ConsumptionRepository extends JpaRepository<ConsumptionEntity, Long> {

	@Query("select new com.hardnets.coop.model.dto.ReadingsDto(c.id, c.reading,c.readingDate) from ConsumptionEntity c where c.waterMeter.id = ?1 order by c.readingDate desc")
	List<ReadingsDto> findAllByWaterMeter(Long waterMeterId);

	@Query(value = "select new com.hardnets.coop.model.dto.response.ResumeConsumptionDetailDto(wm.serial, c.waterMeter.client.dni, "
			+ "c.waterMeter.client.clientType, c.waterMeter.client.fullName, "
			+ "c.reading, c.id) from ConsumptionEntity c inner join c.waterMeter wm where c.period.id = ?1", countQuery = "select count(c) from ConsumptionEntity c where c.period.id = ?1")
	Page<ResumeConsumptionDetailDto> findAllByPeriod(Long id, Pageable pageable);

	List<ConsumptionEntity> findAllByPeriod(PeriodEntity period);

	@Query(value = "select new com.hardnets.coop.model.dto.response.ConsumptionClientDetailDto(c.readingDate, pe.endDate, c.reading) "
			+ "from ConsumptionEntity c inner join c.waterMeter.client cl inner join c.period pe where cl.dni = ?1 and c.reading > 0 order by pe.id desc", countQuery = "select count(c) from ConsumptionEntity c inner join c.waterMeter.client cl where cl.dni = ?1 and c.reading > 0")
	Page<ConsumptionClientDetailDto> findAllByClient(String rut, Pageable pageable);

	@Query(value = "select con from ConsumptionEntity con where con.waterMeter.id = :water_id and con.period.id = "
			+ ":period_id")
	Optional<ConsumptionEntity> findAllByPeriodAndWaterMeter(@Param("period_id") Long period_id,
			@Param("water_id") Long water_id);
}
